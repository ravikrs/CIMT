package de.rwth.i9.cimt.service.similarity.semanticmeasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.util.ExceptionUtils;
import org.openrdf.model.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.service.similarity.SimilarityRelatednessService;
import slib.graph.algo.utils.GAction;
import slib.graph.algo.utils.GActionType;
import slib.graph.algo.utils.GraphActionExecutor;
import slib.graph.io.conf.GDataConf;
import slib.graph.io.loader.wordnet.GraphLoader_Wordnet;
import slib.graph.io.util.GFormat;
import slib.graph.model.graph.G;
import slib.graph.model.impl.graph.memory.GraphMemory;
import slib.graph.model.impl.repo.URIFactoryMemory;
import slib.graph.model.repo.URIFactory;
import slib.indexer.wordnet.IndexerWordNetBasic;
import slib.sml.sm.core.engine.SM_Engine;
import slib.sml.sm.core.metrics.ic.utils.IC_Conf_Topo;
import slib.sml.sm.core.metrics.ic.utils.ICconf;
import slib.sml.sm.core.utils.SMConstants;
import slib.sml.sm.core.utils.SMconf;
import slib.utils.ex.SLIB_Exception;

@Service
public class SRWordNet implements SimilarityRelatednessService {
	private static final Logger log = LoggerFactory.getLogger(SRWordNet.class);
	// Location of WordNet Data
	private @Value("${cimt.home}") String cimtHome;
	private static G wordnet = null;
	private static SM_Engine engine = null;
	private static IndexerWordNetBasic indexWordnetNoun;
	private static IndexerWordNetBasic indexWordnetAdj;
	private static IndexerWordNetBasic indexWordnetVerb;
	private static IndexerWordNetBasic indexWordnetAdv;

	private void init() throws SLIB_Exception {

		if (wordnet != null) {
			return;
		}

		// We create the graph
		URIFactory factory = URIFactoryMemory.getSingleton();
		URI guri = factory.getURI("http://graph/wordnet/");
		wordnet = new GraphMemory(guri);
		String dataloc = cimtHome + "src/main/resources/en/wordnet3.0/dict/";

		// We load the data into the graph
		GraphLoader_Wordnet loader = new GraphLoader_Wordnet();

		GDataConf dataNoun = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.noun");
		GDataConf dataVerb = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.verb");
		GDataConf dataAdj = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.adj");
		GDataConf dataAdv = new GDataConf(GFormat.WORDNET_DATA, dataloc + "data.adv");

		loader.populate(dataNoun, wordnet);
		loader.populate(dataVerb, wordnet);
		loader.populate(dataAdj, wordnet);
		loader.populate(dataAdv, wordnet);
		// We create an index to map the nouns to the vertices of the graph
		// We only build an index for the nouns in this example
		String dataNounIndex = dataloc + "index.noun";
		String dataVerbIndex = dataloc + "index.verb";
		String dataAdjIndex = dataloc + "index.adj";
		String dataAdvIndex = dataloc + "index.adv";

		indexWordnetNoun = new IndexerWordNetBasic(factory, wordnet, dataNounIndex);
		indexWordnetVerb = new IndexerWordNetBasic(factory, wordnet, dataVerbIndex);
		indexWordnetAdj = new IndexerWordNetBasic(factory, wordnet, dataAdjIndex);
		indexWordnetAdv = new IndexerWordNetBasic(factory, wordnet, dataAdvIndex);

		engine = new SM_Engine(wordnet);

	}

	@Override
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2, String algorithmName) {
		double relatednessScore = 0.0;
		try {
			if (wordnet == null) {
				init();
			}
			GAction addRoot = new GAction(GActionType.REROOTING);
			GraphActionExecutor.applyAction(addRoot, wordnet);
			Set<String> uniqueVector1 = new HashSet<>(vector1);
			Set<String> uniqueVector2 = new HashSet<>(vector2);
			Set<URI> vector1Uris = getWordNetURIs(uniqueVector1);
			Set<URI> vector2Uris = getWordNetURIs(uniqueVector2);

			ICconf iconf = new IC_Conf_Topo(SMConstants.FLAG_ICI_SECO_2004);
			SMconf confPairwise = new SMconf(SMConstants.FLAG_SIM_PAIRWISE_DAG_NODE_LIN_1998);
			SMconf confGroupwise = new SMconf(SMConstants.FLAG_SIM_GROUPWISE_AVERAGE);
			confPairwise.setICconf(iconf);

			// we compute the semantic similarities
			relatednessScore = engine.compare(confGroupwise, confPairwise, vector1Uris, vector2Uris);

		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));

		}

		return relatednessScore;
	}

	@Override
	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2, String algorithmName) {
		List<List<Double>> score = new ArrayList<>();
		List<Set<URI>> vector1Uris = new ArrayList<>();
		List<Set<URI>> vector2Uris = new ArrayList<>();
		List<Double> rowScore;
		try {
			if (wordnet == null) {
				init();
			}

			GAction addRoot = new GAction(GActionType.REROOTING);
			GraphActionExecutor.applyAction(addRoot, wordnet);
			ICconf iconf = new IC_Conf_Topo(SMConstants.FLAG_ICI_SECO_2004);
			SMconf confPairwise = new SMconf(SMConstants.FLAG_SIM_PAIRWISE_DAG_NODE_LIN_1998);
			SMconf confGroupwise = new SMconf(SMConstants.FLAG_SIM_GROUPWISE_AVERAGE);
			confPairwise.setICconf(iconf);

			for (String token : vector1) {
				vector1Uris.add(getWordNetURIsforString(token));
			}
			for (String token : vector2) {
				vector2Uris.add(getWordNetURIsforString(token));
			}

			for (Set<URI> vector1Uri : vector1Uris) {
				rowScore = new ArrayList<>();
				for (Set<URI> vector2Uri : vector1Uris) {
					rowScore.add(engine.compare(confGroupwise, confPairwise, vector1Uri, vector2Uri));
				}
				score.add(rowScore);
			}

		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}

		return score;
	}

	private Set<URI> getWordNetURIs(Set<String> uniqueVector1) {
		Set<URI> vectorUris = new HashSet<>();
		for (String token : uniqueVector1) {
			addUris(getWordNetURIsforString(token), vectorUris);
		}
		return vectorUris;
	}

	private Set<URI> getWordNetURIsforString(String token) {
		Set<URI> vectorUris = new HashSet<>();
		addUris(indexWordnetNoun.get(token), vectorUris);
		addUris(indexWordnetVerb.get(token), vectorUris);
		addUris(indexWordnetAdj.get(token), vectorUris);
		addUris(indexWordnetAdv.get(token), vectorUris);
		return vectorUris;
	}

	private void addUris(Set<URI> srcUris, Set<URI> destUris) {
		if (srcUris != null && !srcUris.isEmpty()) {
			destUris.addAll(srcUris);
		}
	}

}
