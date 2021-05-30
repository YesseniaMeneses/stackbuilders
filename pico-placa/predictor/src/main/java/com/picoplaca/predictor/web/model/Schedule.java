package com.picoplaca.predictor.web.model;

public class Schedule {

    public String date;
    public String hour;

    public Schedule(String date, String hour) {
        this.date = date;
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
