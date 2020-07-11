package com.virtualparadigm.fintrader.tool.chartloader.util;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class FormatUtil
{
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd[['T'][HH][:mm][:ss][.SSS]]";
    public static DateTimeFormatter DATE_TIME_FORMATTER =  
            new DateTimeFormatterBuilder().appendPattern(DATE_TIME_PATTERN)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();     

}