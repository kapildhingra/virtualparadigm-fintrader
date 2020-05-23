package com.virtualparadigm.fintrader.app.chart.service.api;

import java.util.Set;

import com.vparadigm.shared.comp.common.identity.GUID;

public class ChartSeriesDTO 
{
	private static final long serialVersionUID = 1L;
	private Set<SampleSeriesDTO> sampleSeriesSet;
	
	public ChartSeriesDTO()
	{
		super();
	}
	public ChartSeriesDTO(
			GUID guid, 
			String symbol, 
			SampleDTOFrequency sampleDTOFrequency, 
			String name, 
			Set<SampleSeriesDTO> sampleSeriesSet)
	{
		this.sampleSeriesSet = sampleSeriesSet;
	}
	
	public Set<SampleSeriesDTO> getSampleSeriesSet()
	{
		return sampleSeriesSet;
	}
	public void setSampleSeriesSet(Set<SampleSeriesDTO> sampleSeriesSet)
	{
		this.sampleSeriesSet = sampleSeriesSet;
	}
	
}