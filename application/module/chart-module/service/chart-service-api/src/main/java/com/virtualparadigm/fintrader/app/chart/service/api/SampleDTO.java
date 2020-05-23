package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;
import java.math.BigDecimal;

import org.joda.time.DateTime;

public class SampleDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private DateTime dateTime;
	private BigDecimal value;
	
	public SampleDTO()
	{
		super();
	}
	public SampleDTO(DateTime dateTime, BigDecimal value)
	{
		super();
		this.dateTime = dateTime;
		this.value = value;
	}
	public DateTime getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(DateTime dateTime)
	{
		this.dateTime = dateTime;
	}
	public BigDecimal getValue()
	{
		return value;
	}
	public void setValue(BigDecimal value)
	{
		this.value = value;
	}
}