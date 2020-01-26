package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

public class SampleData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String field;
	private BigDecimal value;
	public SampleData(String field, BigDecimal value)
	{
		super();
		this.field = field;
		this.value = value;
	}
	public String getField()
	{
		return field;
	}
	public BigDecimal getValue()
	{
		return value;
	}
	
}