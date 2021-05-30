package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.entities.Date;
import com.example.servingwebcontent.entities.MeanOfTransport;
import com.example.servingwebcontent.entities.SearchInput;
import com.example.servingwebcontent.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("plateNumber, date, hour")
public class GreetingController {

	@Autowired
	RuleService service;

	@RequestMapping(value="/greeting", method = RequestMethod.POST)
	public String greeting(@RequestParam String plateNumber,
						   @RequestParam String date,
						   @RequestParam String hour,  ModelMap model) {

		SearchInput searchInput = new SearchInput(new MeanOfTransport(plateNumber),
				 								  new Date(date, hour));

		Boolean carOnTheRoadAllowed = service.validateRules(searchInput);
		if (carOnTheRoadAllowed){
			model.put("plateNumber", "OK");
		} else {
			model.put("plateNumber", "WRONG");
		}
		return "greeting";
	}

}
