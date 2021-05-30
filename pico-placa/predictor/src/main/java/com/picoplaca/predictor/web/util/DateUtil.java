package com.example.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static Integer getDayOfDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getDayOfWeek().getValue();
    }

    public static LocalTime getHour(String hour){
        LocalTime localTime = LocalTime.parse(hour);
        return localTime;
    }
}
