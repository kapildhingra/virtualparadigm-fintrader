package com.virtualparadigm.fintrader.app.chart.rest.api;

import java.io.Serializable;

public class Chart implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final long id;
	private final String message;
	public Chart(long id, String message)
	{
		super();
		this.id = id;
		this.message = message;
	}
	public long getId()
	{
		return id;
	}
	public String getMessage()
	{
		return message;
	}
}