package de.rwth.i9.cimt.service.kpextraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.test.AppRake;
import uk.ac.shef.dcs.jate.JATEException;
import uk.ac.shef.dcs.jate.model.JATETerm;

@Service("rakeKPExtraction")
public class RAKEKPExtraction {
	private static final Logger logger = LoggerFactory.getLogger(RAKEKPExtraction.class);

	public List<Keyword> extractKeywordRAKE(String text, int numKeywords) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		int keywordCount = 0;
		try {
			List<JATETerm> terms = AppRake.rakeAlgo(text);
			String termString = "";
			for (JATETerm term : terms) {
				termString += term.getString() + "\t" + term.getScore() + "\n";
				Keyword keyword = new Keyword();
				keyword.setKeyword(term.getString());
				keyword.setScore(term.getScore());
				keywords.add(keyword);
				if (++keywordCount > numKeywords)
					break;
			}
		} catch (JATEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keywords;
	}

}
