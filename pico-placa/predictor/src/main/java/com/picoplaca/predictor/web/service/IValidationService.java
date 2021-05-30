package com.picoplaca.predictor.web.service;

import com.picoplaca.predictor.web.model.SearchData;
import com.picoplaca.predictor.web.model.ServiceResponse;

public interface IValidationService {
    ServiceResponse validateRules(SearchData searchData);
}
