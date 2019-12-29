package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;

import com.vparadigm.shared.comp.common.identity.GUID;

public class InstrumentDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private GUID guid;
	private String symbol;
	private String name;
	public InstrumentDTO()
	{
		super();
	}
	public InstrumentDTO(GUID guid, String symbol, String name)
	{
		super();
		this.guid = guid;
		this.symbol = symbol;
		this.name = name;
	}
	
	public GUID getGuid()
	{
		return guid;
	}
	public void setGuid(GUID guid)
	{
		this.guid = guid;
	}
	public String getSymbol()
	{
		return symbol;
	}
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
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