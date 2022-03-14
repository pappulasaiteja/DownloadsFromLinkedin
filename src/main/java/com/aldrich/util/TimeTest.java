package com.aldrich.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeTest 
{
	public static void main(String[] args) throws ParseException 
	{
		List<String> timeList=new ArrayList<>();
		timeList.add("0");timeList.add("1"); timeList.add("2"); timeList.add("3"); timeList.add("4"); timeList.add("5"); timeList.add("6"); timeList.add("7"); timeList.add("8"); timeList.add("9"); timeList.add("10"); timeList.add("11"); timeList.add("12"); timeList.add("13"); timeList.add("14"); timeList.add("15"); timeList.add("16"); timeList.add("17"); timeList.add("18"); timeList.add("19"); timeList.add("20"); timeList.add("21"); timeList.add("22"); timeList.add("23"); timeList.add("24"); 		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssX",Locale.ENGLISH);
		String start="2020-01-01T20:30:00+05:30";
		String end="2020-01-01T22:00:00+05:30";
		Date startDate = formatter.parse(start);
		Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
		calendar.setTime(startDate);   // assigns calendar to given date 
		//calendar.set(Calendar.HOUR_OF_DAY, 24);
		String minuteStart=String.valueOf(calendar.get(Calendar.MINUTE));
		String hourStart=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));// gets hour in 24h format 
		String hourMinuteStart=hourStart+":"+minuteStart; // gets hour in 24h format 
		
		
		
		Date endDate = formatter.parse(end);
		Calendar calendar1 = Calendar.getInstance(); // creates a new calendar instance
		calendar1.setTime(endDate);   // assigns calendar to given date 
		//calendar.set(Calendar.HOUR_OF_DAY, 24);
		String minuteend=String.valueOf(calendar1.get(Calendar.MINUTE));
		String hourEnd=String.valueOf(calendar1.get(Calendar.HOUR_OF_DAY));// gets hour in 24h format 
		String hourMinuteEnd=hourEnd+":"+minuteend;
		System.out.println(hourMinuteStart);
		System.out.println("------------------------------------------");
		System.out.println(hourMinuteEnd);
		System.out.println("------------------------------------------");
		timeList.remove(hourStart);timeList.remove(hourEnd);
		timeList.forEach(name->System.out.println(name));
		
	}

}
