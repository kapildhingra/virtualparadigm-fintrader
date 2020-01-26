package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;
import java.math.BigDecimal;

import org.joda.time.DateTime;

public class SampleDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private DateTime dateTime;
	private BigDecimal measurement;
	public SampleDTO()
	{
		super();
	}
	public SampleDTO(DateTime dateTime, BigDecimal measurement)
	{
		super();
		this.dateTime = dateTime;
		this.measurement = measurement;
	}
	public DateTime getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(DateTime dateTime)
	{
		this.dateTime = dateTime;
	}
	public BigDecimal getMeasurement()
	{
		return measurement;
	}
	public void setMeasurement(BigDecimal measurement)
	{
		this.measurement = measurement;
	}
}