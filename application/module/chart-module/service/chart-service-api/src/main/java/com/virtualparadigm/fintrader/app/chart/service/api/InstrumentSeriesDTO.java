package com.virtualparadigm.fintrader.app.chart.service.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.vparadigm.shared.comp.common.identity.GUID;

public class InstrumentSeriesDTO extends InstrumentDTO
{
	private static final long serialVersionUID = 1L;
	private SampleDTOFrequency sampleDTOFrequency;
	private Interval interval;
	private Map<String, List<BigDecimal>> samplesMap;
//	private Map<String, BigDecimal[]> samplesMap;
	
	public InstrumentSeriesDTO()
	{
		super();
	}
	public InstrumentSeriesDTO(GUID guid, String symbol, String name, SampleDTOFrequency sampleDTOFrequency, Interval interval,
			Map<String, List<BigDecimal>> samplesMap)
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
	public Map<String, List<BigDecimal>> getSamplesMap()
	{
		return samplesMap;
	}
	public void setSamplesMap(Map<String, List<BigDecimal>> samplesMap)
	{
		this.samplesMap = samplesMap;
	}
	
}