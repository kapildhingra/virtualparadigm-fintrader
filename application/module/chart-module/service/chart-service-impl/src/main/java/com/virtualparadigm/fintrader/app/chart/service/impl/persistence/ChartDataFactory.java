package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

public class ChartDataFactory
{
	
//	String userspace, SampleFrequencyData sampleFrequencyData, String symbol, String chartName
	public static ChartData createChartData(String strIdentifier)// throws ParseException
	{
		ChartData chartData = null;
		if(StringUtils.isNotEmpty(strIdentifier))
		{
			String[] parts = strIdentifier.split("\\" + ChartId.DELIMITER);
			if(parts.length != 4)
			{
				throw new RuntimeException("chart identifier did not have 4 parts");
				//throw new ParseException("chart identifier did not have 4 parts", -1);
			}
			chartData = new ChartData(parts[0], SampleFrequencyData.valueOf(parts[1]), parts[2], parts[3]);
		}
		return chartData;
	}
	
	public static ChartData createChartData(ChartId chartId) throws ParseException
	{
		ChartData chartData = null;
		if(chartId != null)
		{
			chartData = ChartDataFactory.createChartData(chartId.asString());
		}
		return chartData;
	}
	
	public static ChartId createChartId(String userspace, SampleFrequencyData sampleFrequencyData, String symbol, String chartName)
	{
		StringBuffer strBuf = new StringBuffer();
		if(StringUtils.isNotEmpty(userspace))
		{
			strBuf.append(userspace);
		}
		else
		{
			strBuf.append(ChartData.DEFAULT_USERSPACE);
		}
		strBuf.append(ChartId.DELIMITER);
		
		if(sampleFrequencyData != null)
		{
			strBuf.append(sampleFrequencyData.name());
		}
		else
		{
			strBuf.append(ChartData.DEFAULT_SAMPLE_FREQUENCY.name());
		}
		strBuf.append(ChartId.DELIMITER);
		
		if(StringUtils.isNotEmpty(symbol))
		{
			strBuf.append(symbol);
		}
		else
		{
			strBuf.append(ChartData.DEFAULT_SYMBOL);
		}
		strBuf.append(ChartId.DELIMITER);


		if(StringUtils.isNotEmpty(chartName))
		{
			strBuf.append(chartName);
		}
		else
		{
			strBuf.append(ChartData.DEFAULT_CHART_NAME);
		}
		strBuf.append(ChartId.DELIMITER);
		
		return new ChartId(strBuf.toString());
	}
	
}