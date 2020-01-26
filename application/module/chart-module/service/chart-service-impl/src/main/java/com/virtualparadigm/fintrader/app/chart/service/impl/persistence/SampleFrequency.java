package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

public enum SampleFrequency
{
	MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR;
	
    public static SampleFrequency getSampleFrequency(String strPeriodType)
    {
    	return SampleFrequency.valueOf(strPeriodType.toUpperCase());
    }
}

