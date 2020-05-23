package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

public class ChartId implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String DELIMITER = "-";
	
	private String identifier;
	
	public ChartId(String identifier)
	{
		super();
		this.identifier = identifier;
	}

	public String asString()
	{
		return this.identifier;
	}
}