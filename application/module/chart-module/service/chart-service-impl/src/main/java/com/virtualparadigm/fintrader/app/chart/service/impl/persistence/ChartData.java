package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class ChartData implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_USERSPACE = "global";
	public static final String DEFAULT_SYMBOL = "defaultsymbol";
	public static final SampleFrequencyData DEFAULT_SAMPLE_FREQUENCY = SampleFrequencyData.DAY;
	public static final String DEFAULT_CHART_NAME = "defaultchart";
	
	private ChartId chartId;
	private String userspace;
	private SampleFrequencyData sampleFrequencyData;
	private String symbol;
	private String chartName;
	
	
	public ChartData(String userspace, SampleFrequencyData sampleFrequencyData, String symbol, String chartName)
	{
		super();
		this.userspace = userspace;
		this.symbol = symbol;
		this.sampleFrequencyData = sampleFrequencyData;
		this.chartName = chartName;
		this.chartId = ChartDataFactory.createChartId(userspace, sampleFrequencyData, symbol, chartName);
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

	public SampleFrequencyData getSampleFrequencyData()
	{
		return sampleFrequencyData;
	}

	public String getChartName()
	{
		return chartName;
	}

}