package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class ChartVectorVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public enum FIELD {DATETIME, OPEN, HIGH, LOW, CLOSE, VOLUME};
	
    @JsonDeserialize(using=LocalDateTimeDeserializer.class)
    @JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime dateTime;
    
    Map<String, BigDecimal> valuesMap = new LinkedHashMap<String, BigDecimal>();
    
	public ChartVectorVO()
	{
		super();
	}

	public ChartVectorVO(LocalDateTime dateTime, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal volume)
	{
		super();
		this.dateTime = dateTime;
		this.valuesMap.put(FIELD.OPEN.name(), open);
		this.valuesMap.put(FIELD.HIGH.name(), high);
		this.valuesMap.put(FIELD.LOW.name(), low);
		this.valuesMap.put(FIELD.CLOSE.name(), close);
		this.valuesMap.put(FIELD.VOLUME.name(), volume);
	}
	
	public LocalDateTime getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime)
	{
		this.dateTime = dateTime;
	}

	public Map<String, BigDecimal> getValues()
	{
		return this.valuesMap;
	}
	
	public BigDecimal getOpen()
	{
		return this.valuesMap.get(FIELD.OPEN.name());
	}
	public BigDecimal getHigh()
	{
		return this.valuesMap.get(FIELD.HIGH.name());
	}
	public BigDecimal getLow()
	{
		return this.valuesMap.get(FIELD.LOW.name());
	}
	public BigDecimal getClose()
	{
		return this.valuesMap.get(FIELD.CLOSE.name());
	}
	public BigDecimal getVolume()
	{
		return this.valuesMap.get(FIELD.VOLUME.name());
	}
}
