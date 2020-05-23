package com.virtualparadigm.fintrader.app.chart.service.api;

import java.math.BigDecimal;
import java.util.Map;

import org.joda.time.DateTime;

public class ChartVectorDTO 
{
	private static final long serialVersionUID = 1L;
	private DateTime dateTIme;
	private Map<String, BigDecimal> valueMap;
	
	public ChartVectorDTO()
	{
		super();
	}

	public ChartVectorDTO(DateTime dateTIme, Map<String, BigDecimal> valueMap)
	{
		super();
		this.dateTIme = dateTIme;
		this.valueMap = valueMap;
	}

	public DateTime getDateTIme()
	{
		return dateTIme;
	}

	public void setDateTIme(DateTime dateTIme)
	{
		this.dateTIme = dateTIme;
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