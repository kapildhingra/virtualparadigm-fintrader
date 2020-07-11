package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;

public class UserSpaceDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String name;
	
	public UserSpaceDTO()
	{
		super();
	}

	public UserSpaceDTO(String identifier, String name)
	{
		super();
		this.identifier = identifier;
		this.name = name;
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
//	public static String buildIdentifier(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String name)
//	{
//		return userSpace + "." + sampleDTOFrequency + "." + symbol + "." + name;
//	}
}