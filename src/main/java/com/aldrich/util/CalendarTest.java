package com.aldrich.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

public class CalendarTest 
{
	public static void main(String[] args) 
	{
		//Date today = new Date();
		Date yourDate = DateUtils.addDays(new Date(), -21);
        System.out.println("Decrement one day from date in Java " + toddMMyy(yourDate));
	}
	public static String toddMMyy(Date day){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(day);
        return date;
    }

}
