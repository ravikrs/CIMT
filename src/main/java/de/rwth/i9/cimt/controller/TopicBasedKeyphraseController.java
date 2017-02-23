package de.rwth.i9.cimt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.model.Textbody;
import de.rwth.i9.cimt.service.kpextraction.topic.KeyClusterKPExtraction;
import de.rwth.i9.cimt.service.kpextraction.topic.TopicalPageRankKPExtraction;

@RestController
@RequestMapping("/kpextraction/topic")
public class TopicBasedKeyphraseController {
	private static final Logger log = LoggerFactory.getLogger(KeyphraseController.class);
	@Autowired
	KeyClusterKPExtraction keyClusterKPExtraction;
	@Autowired
	TopicalPageRankKPExtraction topicalPageRankKPExtraction;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getKP() {
		return "Topic based keyphrase extraction";
	}

	@RequestMapping(value = "/keycluster", method = RequestMethod.GET)
	public ModelAndView getKPKeyCluster(Model model) {
		log.info("Inside the getKPKeyCluster");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/topic/keycluster/keyclusterview", "model", "objectName");
	}

	@RequestMapping(value = "/keycluster", method = RequestMethod.POST)
	public List<Keyword> postKPKeyCluster(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return keyClusterKPExtraction.extractKeyword(textbody.getText(), numKeyword);
	}

	@RequestMapping(value = "/tpr", method = RequestMethod.GET)
	public ModelAndView getKPTopicalPageRank(Model model) {
		log.info("Inside the getKPTopicalPageRank");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/topic/tpr/tprview", "modelName", "objectName");
	}

	@RequestMapping(value = "/tpr", method = RequestMethod.POST)
	public List<Keyword> postKPTopicalPageRank(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return topicalPageRankKPExtraction.extractKeywordTPR(textbody.getText(), numKeyword);
	}

	@RequestMapping(value = "/tr", method = RequestMethod.GET)
	public ModelAndView getKPTopicRank(Model model) {
		log.info("Inside the getKPTopicRank");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/topic/tr/trview", "modelName", "objectName");
	}

	@RequestMapping(value = "/tr", method = RequestMethod.POST)
	public List<Keyword> postKPTopicRank(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return topicalPageRankKPExtraction.extractKeywordTR(textbody.getText(), numKeyword);
	}
}