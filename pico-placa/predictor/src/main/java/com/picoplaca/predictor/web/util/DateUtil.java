package com.picoplaca.predictor.web.util;

import com.picoplaca.predictor.web.exception.BusinessException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    static Logger logger = LogManager.getLogger(DateUtil.class);

    public static Integer getDayOfDate(String date) throws BusinessException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate.getDayOfWeek().getValue();
        } catch(Exception e){
            logger.log(Level.ERROR, e);
            throw new BusinessException(1, Constant.DATE_ERROR);
        }
    }

    public static LocalTime getHour(String hour) throws BusinessException {
        try {
            LocalTime localTime = LocalTime.parse(hour);
            return localTime;
        } catch(Exception e){
            logger.log(Level.ERROR, e);
            throw new BusinessException(1, Constant.HOUR_ERROR);
        }
    }
}
