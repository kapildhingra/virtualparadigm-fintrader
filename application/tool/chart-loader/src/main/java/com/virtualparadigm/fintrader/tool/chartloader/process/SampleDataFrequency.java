package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.io.Serializable;

import org.joda.time.PeriodType;

public enum SampleDataFrequency implements Serializable
{
	MILLISECOND(PeriodType.millis()), 
	SECOND(PeriodType.seconds()), 
	MINUTE(PeriodType.minutes()), 
	HOUR(PeriodType.hours()), 
	DAY(PeriodType.days()), 
	WEEK(PeriodType.weeks()), 
	MONTH(PeriodType.months()), 
	YEAR(PeriodType.years());
	
    private SampleDataFrequency(PeriodType periodType)
    {
        this.periodType = periodType;
    }
    
    private PeriodType periodType;
    
    public PeriodType getPeriodType()
    {
    	return this.periodType;
    }
    
    public static SampleDataFrequency getSampleFrequency(String strPeriodType)
    {
    	return SampleDataFrequency.valueOf(strPeriodType.toUpperCase());
    }
}

	