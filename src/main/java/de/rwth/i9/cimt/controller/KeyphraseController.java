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
import de.rwth.i9.cimt.service.kpextraction.JATEKPExtraction;
import de.rwth.i9.cimt.service.kpextraction.RAKEKPExtraction;
import de.rwth.i9.cimt.service.kpextraction.TPRKPExtraction;
import de.rwth.i9.cimt.service.kpextraction.TextRankKPExtraction;

@RestController
@RequestMapping("/kpextraction")
public class KeyphraseController {
	private static final Logger logger = LoggerFactory.getLogger(KeyphraseController.class);
	@Autowired
	JATEKPExtraction jateKPExtraction;
	@Autowired
	RAKEKPExtraction rakeKPExtraction;
	@Autowired
	TPRKPExtraction tprKPExtraction;
	@Autowired
	TextRankKPExtraction trKPExtraction;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getKP() {
		return "keyphrase extraction";
	}

	@RequestMapping(value = "/rake", method = RequestMethod.GET)
	public ModelAndView getKPRake(Model model) {
		logger.info("Inside the getKPRake");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/rake/rakeview", "model", "objectName");
	}

	@RequestMapping(value = "/jate", method = RequestMethod.GET)
	public ModelAndView getKPJate(Model model) {
		logger.info("Inside the getKPJate");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/jate/jateview", "model", "objectName");
	}

	@RequestMapping(value = "/tpr", method = RequestMethod.GET)
	public ModelAndView getKPTpr() {
		logger.info("Inside the getKPTpr");
		return new ModelAndView("kpextraction/tpr/tprview", "modelName", "objectName");
	}

	@RequestMapping(value = "/rake", method = RequestMethod.POST)
	public List<Keyword> postKPRAKE(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return rakeKPExtraction.extractKeywordRAKE(textbody.getText(), numKeyword);
	}

	@RequestMapping(value = "/jate", method = RequestMethod.POST)
	public List<Keyword> postKPJATE(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return jateKPExtraction.extractKeywordJATE(textbody.getText(), textbody.getAlgorithmName(), numKeyword);
	}

	@RequestMapping(value = "/tr", method = RequestMethod.GET)
	public ModelAndView getKPTR(Model model) {
		logger.info("Inside the getKPTR");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/tr/trview", "model", "objectName");
	}

	@RequestMapping(value = "/tr", method = RequestMethod.POST)
	public List<Keyword> postKPTR(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return trKPExtraction.extractKeywordTextRank(textbody.getText(), numKeyword);
	}

	@RequestMapping(value = "/trwordnet", method = RequestMethod.GET)
	public ModelAndView getKPTRWordnet(Model model) {
		logger.info("Inside the getKPTR");
		model.addAttribute("textbody", new Textbody());
		return new ModelAndView("kpextraction/tr/trwordnetview", "model", "objectName");
	}

	@RequestMapping(value = "/trwordnet", method = RequestMethod.POST)
	public List<Keyword> postKPTRWordnet(@ModelAttribute Textbody textbody, HttpServletRequest req) {
		int numKeyword = Integer.parseInt(textbody.getNumKeywords());
		if (numKeyword <= 0) {
			numKeyword = 15;
		}
		return trKPExtraction.extractKeywordTextRankWordnet(textbody.getText(), numKeyword);
	}
}
