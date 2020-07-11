package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;

public class ChartDefinitionDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String userSpace;
	private SampleDTOFrequency sampleDTOFrequency;
	private String symbol;
	private String name;
	
	public ChartDefinitionDTO()
	{
		super();
	}

	public ChartDefinitionDTO(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String name)
	{
		super();
		this.identifier = ChartDefinitionDTO.buildIdentifier(userSpace, sampleDTOFrequency, symbol, name);
		this.userSpace = userSpace;
		this.symbol = symbol;
		this.sampleDTOFrequency = sampleDTOFrequency;
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

	public String getUserSpace()
	{
		return userSpace;
	}

	public void setUserSpace(String userSpace)
	{
		this.userSpace = userSpace;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public SampleDTOFrequency getSampleDTOFrequency()
	{
		return sampleDTOFrequency;
	}

	public void setSampleDTOFrequency(SampleDTOFrequency sampleDTOFrequency)
	{
		this.sampleDTOFrequency = sampleDTOFrequency;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public static String buildIdentifier(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String name)
	{
		return userSpace + "." + sampleDTOFrequency + "." + symbol + "." + name;
	}
}