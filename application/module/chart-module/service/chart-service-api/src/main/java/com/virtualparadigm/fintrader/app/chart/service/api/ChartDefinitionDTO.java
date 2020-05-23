package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;

public class ChartDefinitionDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String userSpace;
	private String symbol;
	private SampleDTOFrequency sampleDTOFrequency;
	private String name;
	
	public ChartDefinitionDTO()
	{
		super();
	}

	public ChartDefinitionDTO(String userSpace, String identifier, String symbol, SampleDTOFrequency sampleDTOFrequency, String name)
	{
		super();
		this.userSpace = userSpace;
		this.identifier = identifier;
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
}