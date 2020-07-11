package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class ChartRecord implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_USERSPACE = "global";
	public static final String DEFAULT_SYMBOL = "defaultsymbol";
	public static final SampleRecordFrequency DEFAULT_SAMPLE_FREQUENCY = SampleRecordFrequency.DAY;
	public static final String DEFAULT_CHART_NAME = "defaultchart";
	
	private ChartId chartId;
	private String userspace;
	private SampleRecordFrequency sampleFrequencyData;
	private String symbol;
	private String chartName;
	
	
	public ChartRecord(String userspace, SampleRecordFrequency sampleFrequencyData, String symbol, String chartName)
	{
		super();
		this.userspace = userspace;
		this.symbol = symbol;
		this.sampleFrequencyData = sampleFrequencyData;
		this.chartName = chartName;
		this.chartId = new ChartId(sampleFrequencyData, symbol, chartName);
//		this.chartId = new ChartId(userspace, sampleFrequencyData, symbol, chartName);
//		this.chartId = ChartRecordFactory.createChartId(userspace, sampleFrequencyData, symbol, chartName);
	}

	public ChartId getChartId()
	{
		return chartId;
	}

	public String getUserspace()
	{
		return userspace;
	}

	public void setChartName(String name)
	{
		this.chartName = name;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public SampleRecordFrequency getSampleRecordFrequency()
	{
		return sampleFrequencyData;
	}

	public String getChartName()
	{
		return chartName;
	}

}