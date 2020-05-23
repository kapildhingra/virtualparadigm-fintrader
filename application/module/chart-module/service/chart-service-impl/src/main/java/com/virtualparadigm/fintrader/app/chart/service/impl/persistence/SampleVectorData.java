package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class SampleVectorData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private long gmtTimestamp;
	private SampleData[] sampleData;
	public SampleVectorData(long gmtTimestamp, SampleData[] sampleData)
	{
		super();
		this.gmtTimestamp = gmtTimestamp;
		this.sampleData = sampleData;
	}
	public long getGmtTimestamp()
	{
		return gmtTimestamp;
	}
	public SampleData[] getSampleData()
	{
		return sampleData;
	}
}