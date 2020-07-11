package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

public class SampleRecord implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String field;
	private BigDecimal value;
	private String unitOfMeasure;
	
	public SampleRecord(String field, BigDecimal value, String unitOfMeasure)
	{
		super();
		this.field = field;
		this.value = value;
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getField()
	{
		return field;
	}
	public BigDecimal getValue()
	{
		return value;
	}
	public String getUnitOfMeasure()
	{
		return unitOfMeasure;
	}
	
}