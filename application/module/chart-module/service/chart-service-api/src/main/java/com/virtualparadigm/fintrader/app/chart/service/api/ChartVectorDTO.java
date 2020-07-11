package com.virtualparadigm.fintrader.app.chart.service.api;

import java.math.BigDecimal;
import java.util.Map;

import org.joda.time.DateTime;

public class ChartVectorDTO 
{
	private static final long serialVersionUID = 1L;
	private DateTime dateTime;
	//TODO: look into adding unit of measure to values
	private Map<String, BigDecimal> valueMap;
	
	public ChartVectorDTO()
	{
		super();
	}

	public ChartVectorDTO(DateTime dateTIme, Map<String, BigDecimal> valueMap)
	{
		super();
		this.dateTime = dateTIme;
		this.valueMap = valueMap;
	}

	public DateTime getDateTime()
	{
		return dateTime;
	}
	public long getDateTimeMillis()
	{
		long dateTimeMillis = -1;
		if(this.dateTime != null)
		{
			dateTimeMillis = this.dateTime.getMillis();
		}
		return dateTimeMillis;
	}

	public void setDateTime(DateTime dateTIme)
	{
		this.dateTime = dateTIme;
	}

	public Map<String, BigDecimal> getValueMap()
	{
		return valueMap;
	}

	public void setValueMap(Map<String, BigDecimal> valueMap)
	{
		this.valueMap = valueMap;
	}
	
}