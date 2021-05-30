package com.picoplaca.predictor.web.controller;

import com.picoplaca.predictor.web.model.Date;
import com.picoplaca.predictor.web.model.MeanOfTransport;
import com.picoplaca.predictor.web.model.SearchInput;
import com.picoplaca.predictor.web.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValidatorController {

	@Autowired
    RuleService service;

	@RequestMapping(value = "/picoplaca", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		return "predictor";
	}


	@RequestMapping(value="/picoplaca", method = RequestMethod.POST)
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
		return "predictor";
	}
}
