package com.picoplaca.predictor.web.rule;

import com.picoplaca.predictor.web.model.Date;
import com.picoplaca.predictor.web.model.MeanOfTransport;
import com.picoplaca.predictor.web.model.SearchInput;

public class PicoPlacaRule extends SearchInput {

    public PicoPlacaRule(MeanOfTransport meanOfTransport, Date date) {
        super(meanOfTransport, date);
    }
}
