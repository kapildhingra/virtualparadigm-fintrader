package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

public enum SampleFrequencyData
{
	MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR;
	
    public static SampleFrequencyData getSampleFrequency(String strPeriodType)
    {
    	return SampleFrequencyData.valueOf(strPeriodType.toUpperCase());
    }
}

