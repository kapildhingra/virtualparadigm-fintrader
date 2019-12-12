package com.virtualparadigm.fintrader.app.reference.shared.dto;

import java.io.Serializable;

public class Greeting implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final long id;
	private final String message;
	public Greeting(long id, String message)
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