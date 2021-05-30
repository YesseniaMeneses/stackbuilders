package com.example.servingwebcontent.entities;

public class SearchInput {
    MeanOfTransport meanOfTransport;
    Date date;

    //public SearchInput() {}

    public SearchInput(MeanOfTransport meanOfTransport, Date date) {
        this.meanOfTransport = meanOfTransport;
        this.date = date;
    }

    public MeanOfTransport getMeanOfTransport() {
        return meanOfTransport;
    }

    public void setMeanOfTransport(MeanOfTransport meanOfTransport) {
        this.meanOfTransport = meanOfTransport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
