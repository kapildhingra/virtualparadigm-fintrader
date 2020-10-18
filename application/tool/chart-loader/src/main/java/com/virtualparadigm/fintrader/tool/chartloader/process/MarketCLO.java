package com.virtualparadigm.fintrader.tool.chartloader.process;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketCLO
{
//	@JsonProperty("name")
//	private String name;
	
	@JsonProperty("marketIdentifierCode")
	private String marketIdentifierCode;
	
	@JsonProperty("dateTimeZone")
	private DateTimeZone dateTimeZone;
	
//	@JsonProperty("marketLocation")
//	private MarketLocationCLO marketLocationCLO;
	
	public MarketCLO(String marketIdentifierCode, DateTimeZone dateTimeZone)
	{
		super();
		this.marketIdentifierCode = marketIdentifierCode;
		this.dateTimeZone = dateTimeZone;
	}
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
		MarketCLO that = (MarketCLO)obj;
		EqualsBuilder builder = new EqualsBuilder();
	    builder.append(this.getMarketIdentifierCode(), that.getMarketIdentifierCode());
		return builder.isEquals();
	}
	
}