package de.rwth.i9.cimt.service.kpextraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.util.JateUtil;
import uk.ac.shef.dcs.jate.JATEException;
import uk.ac.shef.dcs.jate.model.JATETerm;

@Service("jateKPExtraction")
public class JATEKPExtraction {
	private static final Logger logger = LoggerFactory.getLogger(JATEKPExtraction.class);

	public List<Keyword> extractKeywordJATE(String textbody, String algorithmName, int numKeywords) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		int keywordCount = 0;
		try {
			List<JATETerm> terms = new ArrayList<JATETerm>();
			switch (algorithmName) {
			case "TTF":
				terms = JateUtil.TTFAlgo(textbody);
				break;

			case "ATTF":
				terms = JateUtil.ATTFAlgo(textbody);
				break;

			case "TFIDF":
				terms = JateUtil.TFIDFAlgo(textbody);
				break;

			case "RIDF":
				terms = JateUtil.RIDFAlgo(textbody);
				break;

			case "CValue":
				terms = JateUtil.CValueAlgo(textbody);
				break;

			case "ChiSquare":
				terms = JateUtil.ChiSquareAlgo(textbody);
				break;

			case "RAKE":
				terms = JateUtil.RAKEAlgo(textbody);
				break;

			case "Weirdness":
				terms = JateUtil.WeirdnessAlgo(textbody);
				break;

			case "GlossEx":
				terms = JateUtil.GlossExAlgo(textbody);
				break;

			case "TermEx":
				terms = JateUtil.TermExAlgo(textbody);
				break;
			default:
				break;
			}
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
