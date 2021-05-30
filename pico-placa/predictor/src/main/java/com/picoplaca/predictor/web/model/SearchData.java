package com.picoplaca.predictor.web.model;

public class SearchData {

    MeanOfTransport meanOfTransport;
    Schedule date;

    public MeanOfTransport getMeanOfTransport() {
        return meanOfTransport;
    }

    public void setMeanOfTransport(MeanOfTransport meanOfTransport) {
        this.meanOfTransport = meanOfTransport;
    }

    public Schedule getDate() {
        return date;
    }

    public void setDate(Schedule date) {
        this.date = date;
    }
}
