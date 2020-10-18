package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketLocationVO
{
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("dateTimeZone")
	private DateTimeZone dateTimeZone;
	
	public MarketLocationVO(String country, String city, DateTimeZone dateTimeZone)
	{
		super();
		this.country = country;
		this.city = city;
		this.dateTimeZone = dateTimeZone;
	}
	public MarketLocationVO(DateTimeZone dateTimeZone)
	{
		super();
		this.country = country;
		this.city = city;
		this.dateTimeZone = dateTimeZone;
	}
	
	public String getCountry()
	{
		return country;
	}
	public String getCity()
	{
		return city;
	}
	public DateTimeZone getDateTimeZone()
	{
		return dateTimeZone;
	}
}