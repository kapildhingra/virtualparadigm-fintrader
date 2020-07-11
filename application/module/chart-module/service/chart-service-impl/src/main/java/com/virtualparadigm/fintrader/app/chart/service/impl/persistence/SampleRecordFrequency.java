package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

public enum SampleRecordFrequency
{
	MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR;
	
    public static SampleRecordFrequency getSampleFrequency(String strPeriodType)
    {
    	return SampleRecordFrequency.valueOf(strPeriodType.toUpperCase());
    }
}

