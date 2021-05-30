package com.example.servingwebcontent.services;

import com.example.servingwebcontent.entities.Date;
import com.example.servingwebcontent.entities.MeanOfTransport;
import com.example.servingwebcontent.entities.SearchInput;
import com.example.util.DateUtil;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class RuleService {
    Logger logger = LogManager.getLogger(RuleService.class);

    public Boolean validateRules(SearchInput searchInput){
        Date date = searchInput.getDate();
        MeanOfTransport meanOfTransport = searchInput.getMeanOfTransport();

        String plateNumber = meanOfTransport.getPlateNumber();
        Integer day = DateUtil.getDayOfDate(date.getDate());
        LocalTime hour = DateUtil.getHour(date.getHour());

        logger.log(Level.INFO, "DAY: " + day);
        logger.log(Level.INFO, "HOUR: " + hour);

        Boolean dayAllowed = validateDay(plateNumber, day);
        if (!dayAllowed){
            return validateHour(hour);
        } else return true;
    }

    private Boolean validateDay(String plateNumber, Integer day){
        Integer lastDigit = Integer.valueOf(plateNumber.substring(plateNumber.length()-1));
        logger.log(Level.INFO, "lastDigit: " + lastDigit);
        switch (day){
            case 1:
                return !Objects.equals(1, lastDigit) && !Objects.equals(2, lastDigit);
            case 2:
                return !Objects.equals(3, lastDigit) && !Objects.equals(4, lastDigit);
            case 3:
                return !Objects.equals(5, lastDigit) && !Objects.equals(6, lastDigit);
            case 4:
                return !Objects.equals(7, lastDigit) && !Objects.equals(8, lastDigit);
            case 5:
                return !Objects.equals(9, lastDigit) && !Objects.equals(0, lastDigit);
            default: return true;
        }
    }

    private Boolean validateHour(LocalTime hour){
        LocalTime startMorning = LocalTime.of(7, 00);
        LocalTime endMorning = LocalTime.of(9, 30);

        LocalTime startNight = LocalTime.of(16, 00);
        LocalTime endNight = LocalTime.of(19, 30);

        if (hour.isBefore(startMorning) && hour.isAfter(endMorning)
                && hour.isBefore(startNight) && hour.isAfter(endNight)) {
            return true;
        } else return false;
    }
}
