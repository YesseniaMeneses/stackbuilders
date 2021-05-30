package com.picoplaca.predictor.web.service;

import com.picoplaca.predictor.web.model.*;
import com.picoplaca.predictor.web.util.DateUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

@Service
public class ValidationService {
    Logger logger = LogManager.getLogger(ValidationService.class);

    public ServiceResponse validateRules(SearchData searchData) {
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            Schedule date = searchData.getSchedule();
            MeanOfTransport meanOfTransport = searchData.getMeanOfTransport();

            String plateNumber = meanOfTransport.getPlateNumber();
            Integer day = DateUtil.getDayOfDate(date.getDate());
            LocalTime hour = DateUtil.getHour(date.getHour());

            logger.log(Level.INFO, "DAY: " + day);
            logger.log(Level.INFO, "HOUR: " + hour);

            Boolean dayAllowed = validateDay(plateNumber, day);
            logger.log(Level.INFO, "dayAllowed: " + dayAllowed);
            if (!dayAllowed) {
                Boolean hourAllowed = validateHour(hour);
                logger.log(Level.INFO, "hourAllowed: " + hourAllowed);
                serviceResponse.setResult(hourAllowed);
            } else serviceResponse.setResult(true);
        } catch(BusinessException be){
            serviceResponse.setResult(false);
            serviceResponse.setData(be);
        }
        return serviceResponse;
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
        LocalTime startMorning = LocalTime.of(6, 59);
        LocalTime endMorning = LocalTime.of(9, 31);

        LocalTime startNight = LocalTime.of(15, 59);
        LocalTime endNight = LocalTime.of(19, 31);

        if (hour.isAfter(startMorning) && hour.isBefore(endMorning)
                || hour.isAfter(startNight) && hour.isBefore(endNight)) {
            return false;
        } else return true;
    }
}
