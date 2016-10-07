/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: kpatakas
 *
 *  $Id: $
 */

/**
 * JavaDoc comment for source
 */


package com.kpatakas.jpmorgan.simplestock.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getDateByMinuteOffset(int minutes){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,-minutes);
        return now.getTime();

    }
}
