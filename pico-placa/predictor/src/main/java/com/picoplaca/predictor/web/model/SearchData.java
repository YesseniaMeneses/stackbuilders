package com.picoplaca.predictor.web.model;

public class SearchData {

    MeanOfTransport meanOfTransport;
    Schedule schedule;

    public MeanOfTransport getMeanOfTransport() {
        return meanOfTransport;
    }

    public void setMeanOfTransport(MeanOfTransport meanOfTransport) {
        this.meanOfTransport = meanOfTransport;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
