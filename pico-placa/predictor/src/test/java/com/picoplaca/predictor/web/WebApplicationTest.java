/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.picoplaca.predictor.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.picoplaca.predictor.web.controller.ValidatorController;
import com.picoplaca.predictor.web.model.*;
import com.picoplaca.predictor.web.service.ValidationService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@WebMvcTest(controllers = ValidatorController.class)
public class WebApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void homePage() throws Exception {
		// N.B. jsoup can be useful for asserting HTML content
		mockMvc.perform(get("/index.html"))
				.andExpect(content().string(containsString("Go to Pico & Placa Validation")));
	}

	/**
	 * Author: ymeneses
	 * Description: allowed day, allowed hour
	 * @throws Exception
	 */
	@Test
	public void validateAllowedDayAllowedHour() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/05/24", "09:31"));
		searchData.setMeanOfTransport(new MeanOfTransport("ABC1113"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}

	/**
	 * Author: ymeneses
	 * Description: not allowed day, allowed hour
	 * @throws Exception
	 */
	@Test
	public void validateNotAllowedDayAllowedHour() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/05/24", "09:30"));
		searchData.setMeanOfTransport(new MeanOfTransport("ABC111"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(!carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}

	/**
	 * Author: ymeneses
	 * Description: allowed day, not allowed hour
	 * @throws Exception
	 */
	@Test
	public void validateAllowedDayNotAllowedHour() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/05/24", "16:00"));
		searchData.setMeanOfTransport(new MeanOfTransport("ABC111"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(!carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}

	/**
	 * Author: ymeneses
	 * Description: not allowed day, not allowed hour
	 * @throws Exception
	 */
	@Test
	public void validateNotAllowedDayNotAllowedHour() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/05/24", "17:59"));
		searchData.setMeanOfTransport(new MeanOfTransport("ABC111"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(!carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}

	/**
	 * Author: ymeneses
	 * Description: not allowed date
	 * @throws Exception
	 */
	@Test
	public void validateNotAllowedDate() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/13/24", "07:00"));
		searchData.setMeanOfTransport(new MeanOfTransport("ABC111"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(!carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}

	/**
	 * Author: ymeneses
	 * Description: not allowed hour
	 * @throws Exception
	 */
	@Test
	public void validateNotAllowedHour() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/12/24", "25:00"));
		searchData.setMeanOfTransport(new MeanOfTransport("ABC111"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(!carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}

	/**
	 * Author: ymeneses
	 * Description: not allowed plate number
	 * @throws Exception
	 */
	@Test
	public void validateNotAllowedPlateNumber() throws Exception {

		SearchData searchData = new SearchData();
		searchData.setSchedule(new Schedule("2021/13/24", "07:00"));
		searchData.setMeanOfTransport(new MeanOfTransport("12345"));

		ValidationService validationService = new ValidationService();
		ServiceResponse carOnTheRoadAllowed = validationService.validateRules(searchData);
		Assert.isTrue(!carOnTheRoadAllowed.getResult(), "VALIDATION FAILED");
	}


}
