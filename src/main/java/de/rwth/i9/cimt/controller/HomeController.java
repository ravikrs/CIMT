package de.rwth.i9.cimt.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@RestController
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(Locale locale, Model model) {

		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";

	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView view() {
		logger.info("Inside the message/view");
		return new ModelAndView("view", "message", "");
	}

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public ModelAndView view1() {
		logger.info("Inside the message/view1");
		return new ModelAndView("templates/messages/view", "message", "");
	}

	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public ModelAndView view2() {
		logger.info("Inside the message/view1");
		return new ModelAndView("messages/view", "message", "");
	}

}
