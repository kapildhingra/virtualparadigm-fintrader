package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;

public class ChartIdentifier implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String userSpace;
	private SampleDTOFrequency sampleDTOFrequency;
	private String symbol;
	private String chartName;
	
	public ChartIdentifier()
	{
		super();
	}
	public ChartIdentifier(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String chartName)
	{
		super();
		this.userSpace = userSpace;
		this.sampleDTOFrequency = sampleDTOFrequency;
		this.symbol = symbol;
		this.chartName = chartName;
	}
	public String getUserSpace()
	{
		return userSpace;
	}
	public void setUserSpace(String userSpace)
	{
		this.userSpace = userSpace;
	}
	public SampleDTOFrequency getSampleDTOFrequency()
	{
		return sampleDTOFrequency;
	}
	public void setSampleDTOFrequency(SampleDTOFrequency sampleDTOFrequency)
	{
		this.sampleDTOFrequency = sampleDTOFrequency;
	}
	public String getSymbol()
	{
		return symbol;
	}
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	public String getChartName()
	{
		return chartName;
	}
	public void setChartName(String chartName)
	{
		this.chartName = chartName;
	}
}