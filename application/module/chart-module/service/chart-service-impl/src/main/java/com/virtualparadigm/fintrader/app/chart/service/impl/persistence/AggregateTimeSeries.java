package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class AggregateTimeSeries implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private SampleFrequency sampleFrequency;
	
	public AggregateTimeSeries(String name, SampleFrequency sampleFrequency)
	{
		super();
		this.name = name;
		this.sampleFrequency = sampleFrequency;
	}

	public String getName()
	{
		return name;
	}

	public SampleFrequency getSampleFrequency()
	{
		return sampleFrequency;
	}
	
}