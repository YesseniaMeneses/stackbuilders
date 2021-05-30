package com.example.servingwebcontent.rules;

import com.example.servingwebcontent.entities.Date;
import com.example.servingwebcontent.entities.MeanOfTransport;
import com.example.servingwebcontent.entities.SearchInput;

public class PicoPlacaRule extends SearchInput {

    public PicoPlacaRule(MeanOfTransport meanOfTransport, Date date) {
        super(meanOfTransport, date);
    }
}
