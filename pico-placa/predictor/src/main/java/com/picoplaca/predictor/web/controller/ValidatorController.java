package com.picoplaca.predictor.web.controller;

import com.picoplaca.predictor.web.exception.BusinessException;
import com.picoplaca.predictor.web.model.*;
import com.picoplaca.predictor.web.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class ValidatorController {

	@Autowired
	ValidationService service;

	@RequestMapping(value = "/picoplaca", method = RequestMethod.GET)
	public String showPredictorPage() {
		return "predictor";
	}


	@RequestMapping(value="/picoplaca", method = RequestMethod.POST)
	public String validateSchedule(@RequestParam String plateNumber,
						   @RequestParam String date,
						   @RequestParam String hour,  ModelMap model) {

		if (!Objects.isNull(plateNumber) && !Objects.isNull(date) && !Objects.isNull(hour)) {
			SearchData searchData = new SearchData();
			searchData.setSchedule(new Schedule(date, hour));
			searchData.setMeanOfTransport(new MeanOfTransport(plateNumber));

			ServiceResponse carOnTheRoadAllowed = service.validateRules(searchData);
			if (carOnTheRoadAllowed.getResult()) {
				model.put("validation", "YOUR CAR CAN BE ON THE ROAD");
			} else if (Objects.isNull(carOnTheRoadAllowed.getData())) {
				model.put("validation", "YOUR CAR CANNOT BE ON THE ROAD");
			} else {
				BusinessException be = (BusinessException) carOnTheRoadAllowed.getData();
				model.put("validation", be.getErrorMessage());
			}
		}
		return "predictor";
	}
}
