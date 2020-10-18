package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketVO
{
//	@JsonProperty("name")
//	private String name;
	
	@JsonProperty("marketIdentifierCode")
	private String marketIdentifierCode;
	
	@JsonProperty("dateTimeZone")
	private DateTimeZone dateTimeZone;

//	@JsonProperty("marketLocation")
//	private MarketLocationVO marketLocationCLO;
	
//	public MarketVO(String name, String marketIdentifierCode, DateTimeZone dateTimeZone)
//	{
//		super();
//		this.name = name;
//		this.marketIdentifierCode = marketIdentifierCode;
////		this.marketLocationCLO = marketLocationCLO;
//	}
	
	public MarketVO(String marketIdentifierCode, DateTimeZone dateTimeZone)
	{
		this.marketIdentifierCode = marketIdentifierCode;
		this.dateTimeZone = dateTimeZone;
	}
	
//	public String getName()
//	{
//		return name;
//	}
	public String getMarketIdentifierCode()
	{
		return marketIdentifierCode;
	}
	public DateTimeZone getDateTimeZone()
	{
		return dateTimeZone;
	}
	
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder(17,31);
	    builder.append(this.getMarketIdentifierCode());
	    return builder.toHashCode();		
	}
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (obj == this)
		{
			return true;
		}
		if (obj.getClass() != getClass())
		{
			return false;
		}
		MarketVO that = (MarketVO)obj;
		EqualsBuilder builder = new EqualsBuilder();
	    builder.append(this.getMarketIdentifierCode(), that.getMarketIdentifierCode());
		return builder.isEquals();
	}
	
}