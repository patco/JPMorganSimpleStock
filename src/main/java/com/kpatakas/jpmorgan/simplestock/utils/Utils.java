package com.kpatakas.jpmorgan.simplestock.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    private static final Logger T = LoggerFactory.getLogger(Utils.class);
    public static Date getDateByMinuteOffset(int minutes){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,-minutes);
        return now.getTime();

    }

    /**
     * Divides the given two values or returns 0 if the denominator (divisor) is 0.
     * @param numerator     the numerator to divide
     * @param denominator   the denominator to divide by
     */
    public static Double divide(Double numerator, Double denominator) {
        if (denominator.equals(0d)) {
            T.warn( "Returning 0 because denominator is 0");
            return 0d;
        }
        return round(numerator/denominator);
    }


    public static Double geometricMean(ArrayList<Double> data)
    {
        double sum = data.get(0);

        for (int i = 1; i < data.size(); i++) {
            sum *= data.get(i);
        }
        return round(Math.pow(sum, 1.0 / data.size()));
    }

    public static Double round(Double value){
        return new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
