package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class SampleVector implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private long gmtTimestamp;
	private String unitOfMeasure;
	private SampleData[] sampleData;
	public SampleVector(long gmtTimestamp, String unitOfMeasure, SampleData[] sampleData)
	{
		super();
		this.gmtTimestamp = gmtTimestamp;
		this.unitOfMeasure = unitOfMeasure;
		this.sampleData = sampleData;
	}
	public long getGmtTimestamp()
	{
		return gmtTimestamp;
	}
	public String getUnitOfMeasure()
	{
		return unitOfMeasure;
	}
	public SampleData[] getSampleData()
	{
		return sampleData;
	}
}