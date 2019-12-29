package com.virtualparadigm.fintrader.app.chart.service.api;

import java.math.BigDecimal;
import java.util.Map;

import org.joda.time.Interval;

import com.vparadigm.shared.comp.common.identity.GUID;

public class InstrumentSeriesDTO extends InstrumentDTO
{
	private static final long serialVersionUID = 1L;
	private SampleDTOFrequency sampleDTOFrequency;
	private Interval interval;
	private Map<String, BigDecimal[]> samplesMap;
	public InstrumentSeriesDTO()
	{
		super();
	}
	public InstrumentSeriesDTO(GUID guid, String symbol, String name, SampleDTOFrequency sampleDTOFrequency, Interval interval,
			Map<String, BigDecimal[]> samplesMap)
	{
		super(guid, symbol, name);
		this.sampleDTOFrequency = sampleDTOFrequency;
		this.interval = interval;
		this.samplesMap = samplesMap;
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
	
	public Map<String, BigDecimal[]> getSamplesMap()
	{
		return samplesMap;
	}
	public void setSamplesMap(Map<String, BigDecimal[]> samplesMap)
	{
		this.samplesMap = samplesMap;
	}
}