package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class SampleVectorRecord implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private long gmtTimestamp;
	private SampleRecord[] sampleRecord;
	public SampleVectorRecord(long gmtTimestamp, SampleRecord[] sampleData)
	{
		super();
		this.gmtTimestamp = gmtTimestamp;
		this.sampleRecord = sampleData;
	}
	public long getGmtTimestamp()
	{
		return gmtTimestamp;
	}
	public SampleRecord[] getSampleRecords()
	{
		return sampleRecord;
	}
}