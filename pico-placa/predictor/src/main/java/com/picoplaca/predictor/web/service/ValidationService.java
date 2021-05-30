package com.picoplaca.predictor.web.service;

import com.picoplaca.predictor.web.exception.BusinessException;
import com.picoplaca.predictor.web.model.MeanOfTransport;
import com.picoplaca.predictor.web.model.Schedule;
import com.picoplaca.predictor.web.model.SearchData;
import com.picoplaca.predictor.web.model.ServiceResponse;
import com.picoplaca.predictor.web.util.Constant;
import com.picoplaca.predictor.web.util.DateUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

@Service
public class ValidationService implements IValidationService{
    Logger logger = LogManager.getLogger(ValidationService.class);

    public ServiceResponse validateRules(SearchData searchData) {
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            Schedule date = searchData.getSchedule();
            MeanOfTransport meanOfTransport = searchData.getMeanOfTransport();

            String plateNumber = meanOfTransport.getPlateNumber();
            Integer day = DateUtil.getDayOfDate(date.getDate());
            LocalTime hour = DateUtil.getHour(date.getHour());

            if (plateNumber.length() > 0) {
                Integer lastDigit = Integer.valueOf(plateNumber.substring(plateNumber.length() - 1));
                Boolean dayAllowed = validateDay(lastDigit, day);
                logger.log(Level.INFO, "dayAllowed: " + dayAllowed);

                if (!dayAllowed) {
                    Boolean hourAllowed = validateHour(hour);
                    logger.log(Level.INFO, "hourAllowed: " + hourAllowed);
                    serviceResponse.setResult(hourAllowed);
                } else {
                    serviceResponse.setResult(true);
                }
            } else {
                serviceResponse.setResult(false);
                serviceResponse.setData(new BusinessException(1, Constant.PLATE_NUMBER_ERROR_LENGTH));
            }
        } catch(BusinessException be){
            serviceResponse.setResult(false);
            serviceResponse.setData(be);
        } catch (Exception e){
            logger.log(Level.FATAL, e);
            serviceResponse.setResult(false);
            serviceResponse.setData(new BusinessException(1, e.getMessage()));
        }
        logger.log(Level.INFO, "VALIDATE SCHEDULE ---> " + serviceResponse.getResult());
        if(!serviceResponse.getResult()) {
            BusinessException be = (BusinessException) serviceResponse.getData();
            logger.log(Level.INFO, "VALIDATE SCHEDULE MESSAGE---> " + be.getErrorMessage());
        }
        return serviceResponse;
    }

    private Boolean validateDay(Integer lastDigit, Integer day){
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
