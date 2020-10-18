package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.io.Serializable;

import org.joda.time.PeriodType;

public enum SampleCLOFrequency implements Serializable
{
	MILLISECOND(PeriodType.millis()), 
	SECOND(PeriodType.seconds()), 
	MINUTE(PeriodType.minutes()), 
	HOUR(PeriodType.hours()), 
	DAY(PeriodType.days()), 
	WEEK(PeriodType.weeks()), 
	MONTH(PeriodType.months()), 
	YEAR(PeriodType.years());
	
    private SampleCLOFrequency(PeriodType periodType)
    {
        this.periodType = periodType;
    }
    
    private PeriodType periodType;
    
    public PeriodType getPeriodType()
    {
    	return this.periodType;
    }
    
    public static SampleCLOFrequency getSampleFrequency(String strPeriodType)
    {
    	return SampleCLOFrequency.valueOf(strPeriodType.toUpperCase());
    }
}

	