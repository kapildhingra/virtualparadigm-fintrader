package com.virtualparadigm.fintrader.app.chart.service.api;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.Interval;

public class SampleSeriesDTO 
{
	private static final long serialVersionUID = 1L;
	private String name;
	private SampleDTOFrequency sampleDTOFrequency;
	private Interval interval;
	private List<BigDecimal> samples;
//	private BigDecimal[] samples;
	
	public SampleSeriesDTO()
	{
		super();
	}
	public SampleSeriesDTO(String name, SampleDTOFrequency sampleDTOFrequency, Interval interval,
			List<BigDecimal> samples)
	{
		this.name = name;
		this.sampleDTOFrequency = sampleDTOFrequency;
		this.interval = interval;
		this.samples = samples;
	}
	public SampleDTOFrequency getSampleDTOFrequency()
	{
		return sampleDTOFrequency;
	}
	public void setSampleDTOFrequency(SampleDTOFrequency sampleDTOFrequency)
	{
		this.sampleDTOFrequency = sampleDTOFrequency;
	}
	public Interval getInterval()
	{
		return interval;
	}
	public void setInterval(Interval interval)
	{
		this.interval = interval;
	}
	public List<BigDecimal> getSamples()
	{
		return samples;
	}
	public void setSamples(List<BigDecimal> samples)
	{
		this.samples = samples;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
}