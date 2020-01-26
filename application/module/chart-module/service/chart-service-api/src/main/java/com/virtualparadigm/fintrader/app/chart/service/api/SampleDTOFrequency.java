package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;

import org.joda.time.PeriodType;

public enum SampleDTOFrequency implements Serializable
{
	MILLISECOND(PeriodType.millis()), 
	SECOND(PeriodType.seconds()), 
	MINUTE(PeriodType.minutes()), 
	HOUR(PeriodType.hours()), 
	DAY(PeriodType.days()), 
	WEEK(PeriodType.weeks()), 
	MONTH(PeriodType.months()), 
	YEAR(PeriodType.years());
	
    private SampleDTOFrequency(PeriodType periodType)
    {
        this.periodType = periodType;
    }
    
    private PeriodType periodType;
    
    public PeriodType getPeriodType()
    {
    	return this.periodType;
    }
    
    public static SampleDTOFrequency getSampleFrequency(String strPeriodType)
    {
    	return SampleDTOFrequency.valueOf(strPeriodType.toUpperCase());
    }
}

	