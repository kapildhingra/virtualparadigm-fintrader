package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.Serializable;
import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExchangeZone implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("name")
	private String name;
	
	private String utcOffset;
	
	public ExchangeZone(String name, String utcOffset)
	{
		super();
		this.name = name;
		this.utcOffset = utcOffset;
	}

	public String getName()
	{
		return name;
	}

	public String getUtcOffset()
	{
		return utcOffset;
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			ExchangeZone[] exchanges = new ExchangeZone[2];
			exchanges[0] = new ExchangeZone("NYSE", "-5:00");
			exchanges[1] = new ExchangeZone("NASDAQ", "-5:00");
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(exchanges));
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		
	}
}