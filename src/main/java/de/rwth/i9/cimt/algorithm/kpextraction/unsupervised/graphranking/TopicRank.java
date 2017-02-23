package de.rwth.i9.cimt.algorithm.kpextraction.unsupervised.graphranking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.nlp.NLP;
import opennlp.tools.stemmer.PorterStemmer;

public class TopicRank {
	private static final Logger log = LoggerFactory.getLogger(TopicRank.class);
	private static final int CO_OCCURRENCE_WINDOW = 5;
	private static final String NOUN_PHRASE_REGEX_EXPR = "(NN|NNS|NNP|NNPS|JJ|JJR|JJS)*(NN|NNS|NNP|NNPS)";
	private static final Pattern NOUN_PHRASE_PATTERN = Pattern.compile(NOUN_PHRASE_REGEX_EXPR);

	/**
	 * performs TopicRank Keyphrase Algorithm for the input textContent.
	 * 
	 * @param textContent
	 *            input text
	 * @param nlpImpl
	 *            nlp implementation used for sentence detection
	 */
	public static List<Keyword> performTopicRankKE(String textContent, final NLP nlpImpl) {
		Graph<String, DefaultWeightedEdge> textRankGraph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		List<String> tokenVertices = new ArrayList<String>();
		List<Keyword> returnedKeyphrases = new ArrayList<Keyword>();
		List<String> sentencesList = Arrays.asList(nlpImpl.detectSentences(textContent));

		Map<Integer, String> sentenceListMap = IntStream.range(0, sentencesList.size()).boxed()
				.collect(Collectors.toMap(Function.identity(), i -> sentencesList.get(i)));

		Map<Integer, List<String>> sentenceIndexTokenListMap = sentenceListMap.entrySet().stream()
				.collect(
						Collectors
								.toMap(e -> e.getKey(),
										e -> (List<String>) Arrays.asList(nlpImpl.tokenize(e.getValue())).stream()
												.map(token -> token.trim().toLowerCase())
												.collect(Collectors.toList())));

		Set<String> nounAdjTokens = getNounAdjSeqToken(textContent, nlpImpl);

		for (Map.Entry<Integer, String> entry : sentenceListMap.entrySet()) {
			Integer index = entry.getKey();
			String sentence = entry.getValue();
			String[] tokens = sentenceIndexTokenListMap.get(index).toArray(new String[0]);
			String[] posTags = nlpImpl.tagPartOfSpeech(tokens);
			for (int i = 0; i < tokens.length; i++) {
				if (isGoodPos(posTags[i])) {
					String token = tokens[i].trim().toLowerCase();
					if (!tokenVertices.contains(token)) {
						tokenVertices.add(token);
						textRankGraph.addVertex(token);
					}
				}
			}

		}

		for (Map.Entry<Integer, List<String>> entry : sentenceIndexTokenListMap.entrySet()) {
			List<String> tokenList = entry.getValue();
			int tokenIndex = 0;
			for (String token : tokenList) {
				tokenIndex++;
				token = token.trim().toLowerCase();
				if (tokenVertices.contains(token)) {
					int toIndex = tokenIndex + CO_OCCURRENCE_WINDOW;
					toIndex = toIndex > tokenList.size() ? tokenList.size() : toIndex;
					tokenIndex = tokenIndex > toIndex ? toIndex : tokenIndex;
					List<String> subtokenList = tokenList.subList(tokenIndex, toIndex);
					for (String secondToken : subtokenList) {
						secondToken = secondToken.trim().toLowerCase();
						if (tokenVertices.contains(secondToken)) {
							if (!token.equals(secondToken)) {
								textRankGraph.addEdge(token, secondToken);
							}
						}
					}
				}

			}
		}

		PageRank<String, DefaultWeightedEdge> pr = new PageRank<String, DefaultWeightedEdge>(textRankGraph);
		Map<String, Double> prScoreMap = pr.getScores();
		List<String> returnedKeywords = prScoreMap.entrySet().stream().map(x -> x.getKey())
				.collect(Collectors.toList());

		List<String> nonRetainedkeywords = new ArrayList<String>();
		for (String sentence : sentencesList) {
			boolean isKeyphrase = false;
			String keyphrase = "";
			double keyphraseScore = 0.0;
			int phraseCount = 0;
			for (String token : nlpImpl.tokenize(sentence)) {
				String trimmedtoken = token.trim().toLowerCase();
				if (returnedKeywords.contains(trimmedtoken)) {
					keyphrase += trimmedtoken + " ";
					keyphraseScore += prScoreMap.get(trimmedtoken).doubleValue();
					phraseCount++;
					continue;
				} else if (phraseCount > 1) {
					returnedKeyphrases.add(new Keyword(keyphrase.trim(), keyphraseScore));
					nonRetainedkeywords.addAll(Arrays.asList(keyphrase.trim().split("\\s+")));
				}
				keyphrase = "";
				keyphraseScore = 0.0;
				phraseCount = 0;
				isKeyphrase = false;
			}
		}
		returnedKeywords.removeAll(nonRetainedkeywords);
		for (String keywordString : returnedKeywords) {
			returnedKeyphrases.add(new Keyword(keywordString, prScoreMap.get(keywordString).doubleValue()));
		}
		Collections.sort(returnedKeyphrases, Keyword.KeywordComparatorDesc);
		returnedKeyphrases.forEach(k -> System.out.println(k.getKeyword() + k.getScore()));
		return returnedKeyphrases;

	}

	private static boolean isGoodPos(String pos) {
		if (pos.startsWith("NN") || pos.startsWith("JJ"))
			return true;
		return false;
	}

	private static Set<String> getNounAdjSeqToken(String textContent, NLP nlp) {
		Set<String> nounAdjTokens = new HashSet<String>();
		String token = "";
		for (String sentence : nlp.detectSentences(textContent)) {
			String[] tokens = nlp.tokenize(sentence);
			String[] posTags = nlp.tagPartOfSpeech(tokens);
			int index = 0;
			int phraseCount = 0;
			for (String posTag : posTags) {
				if (posTag.startsWith("NN") || posTag.startsWith("JJ")) {
					token += tokens[index] + " ";
					phraseCount++;
					continue;
				} else if (phraseCount >= 1) {
					nounAdjTokens.add(token);
					phraseCount = 0;
				}
			}

		}
		return nounAdjTokens;
	}

	private boolean stemsOverlap(String token1, String token2) {
		PorterStemmer stemmer = new PorterStemmer();
		List<String> tokens1 = Arrays.asList(token1.split("\\s+")).stream().map(t -> stemmer.stem(t))
				.collect(Collectors.toList());
		List<String> tokens2 = Arrays.asList(token2.split("\\s+")).stream().map(t -> stemmer.stem(t))
				.collect(Collectors.toList());
		int totaltokens = tokens1.size() + tokens2.size();
		double overlapScore = 0.0;

		return true;
	}

	private void performHAC() {
	}
}
