package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

public class ChartRecordFactory
{
	
	public static ChartRecord createChartRecord(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName)
	{
		return new ChartRecord(userSpace, sampleRecordFrequency, symbol, chartName);
	}
	public static ChartRecord createChartRecord(String userSpace, String chartIdentifier) throws ParseException
	{
		ChartRecord chartData = null;
		if(StringUtils.isNotEmpty(userSpace) && StringUtils.isNotEmpty(chartIdentifier))
		{
			String[] parts = chartIdentifier.split("\\" + ChartId.DELIMITER);
			if(parts.length != 3)
			{
				//throw new IllegalArgumentException("chart identifier did not have 4 parts");
				throw new ParseException("chart identifier did not have 3 parts", -1);
			}
			chartData = new ChartRecord(userSpace, SampleRecordFrequency.valueOf(parts[0]), parts[0], parts[2]);
		}
		return chartData;
	}
	
	public static ChartRecord createChartRecord(String userSpace, ChartId chartId) throws ParseException
	{
		ChartRecord chartRecord = null;
		if(chartId != null)
		{
			chartRecord = 
					ChartRecordFactory.createChartRecord(
							userSpace, 
							chartId.getSampleRecordFrequency(), 
							chartId.getSymbol(), 
							chartId.getChartName());
		}
		return chartRecord;
	}
	
//	public static ChartId createChartId(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName)
//	{
//		StringBuffer strBuf = new StringBuffer();
//		if(StringUtils.isNotEmpty(userSpace))
//		{
//			strBuf.append(userSpace);
//		}
//		else
//		{
//			strBuf.append(ChartRecord.DEFAULT_USERSPACE);
//		}
//		strBuf.append(ChartId.DELIMITER);
//		
//		if(sampleRecordFrequency != null)
//		{
//			strBuf.append(sampleRecordFrequency.name());
//		}
//		else
//		{
//			strBuf.append(ChartRecord.DEFAULT_SAMPLE_FREQUENCY.name());
//		}
//		strBuf.append(ChartId.DELIMITER);
//		
//		if(StringUtils.isNotEmpty(symbol))
//		{
//			strBuf.append(symbol);
//		}
//		else
//		{
//			strBuf.append(ChartRecord.DEFAULT_SYMBOL);
//		}
//		strBuf.append(ChartId.DELIMITER);
//
//
//		if(StringUtils.isNotEmpty(chartName))
//		{
//			strBuf.append(chartName);
//		}
//		else
//		{
//			strBuf.append(ChartRecord.DEFAULT_CHART_NAME);
//		}
//		
//		return new ChartId(strBuf.toString());
//		//return new ChartId("fintrader_instrument.autogen");
//	}
	
}