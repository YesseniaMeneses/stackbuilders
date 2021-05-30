package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.entities.Date;
import com.example.servingwebcontent.entities.MeanOfTransport;
import com.example.servingwebcontent.entities.SearchInput;
import com.example.servingwebcontent.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

	@Autowired
	RuleService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		model.put("plateNumber", "WRONG");
		return "greeting";
	}


	@RequestMapping(value="/", method = RequestMethod.POST)
	public String greeting(@RequestParam String plateNumber,
						   @RequestParam String date,
						   @RequestParam String hour,  ModelMap model) {

		SearchInput searchInput = new SearchInput(new MeanOfTransport(plateNumber),
				new Date(date, hour));

		Boolean carOnTheRoadAllowed = service.validateRules(searchInput);
		if (carOnTheRoadAllowed){
			model.put("validation", "YOUR CAR CAN BE ON THE ROAD");
		} else {
			model.put("validation", "YOUR CAR CANNOT BE ON THE ROAD");
		}
		return "greeting";
	}
}
