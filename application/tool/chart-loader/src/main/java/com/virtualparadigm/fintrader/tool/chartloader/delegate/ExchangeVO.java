package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.Serializable;
import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExchangeVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("utcOffset")
	private ZoneOffset utcOffset;
	
	public ExchangeVO(String name, int utcHourOffset, int utcMinuteOffset)
	{
		super();
		this.name = name;
		this.utcOffset = ZoneOffset.ofHoursMinutes(utcHourOffset, utcMinuteOffset);
	}

	public String getName()
	{
		return name;
	}

	public ZoneOffset getUtcOffset()
	{
		return utcOffset;
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			ExchangeVO[] exchanges = new ExchangeVO[2];
			exchanges[0] = new ExchangeVO("NYSE", -5, 0);
			exchanges[1] = new ExchangeVO("NASDAQ", -5, 0);
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(exchanges));
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		
	}
}