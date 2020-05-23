package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ChartData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    @JsonDeserialize(using=LocalDateTimeDeserializer.class)
    @JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime dateTime;
	private double open;
	private double high;
	private double low;
	private double close;
	private long volume;
	private double dividendAmount;
	private double splitCoefficient;
	
	public ChartData()
	{
		super();
	}

	public ChartData(LocalDateTime dateTime, double open, double high, double low, double close, long volume, double dividendAmount,
			double splitCoefficient)
	{
		super();
		this.dateTime = dateTime;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.dividendAmount = dividendAmount;
		this.splitCoefficient = splitCoefficient;
	}

	public LocalDateTime getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime)
	{
		this.dateTime = dateTime;
	}

	public double getOpen()
	{
		return open;
	}

	public void setOpen(double open)
	{
		this.open = open;
	}

	public double getHigh()
	{
		return high;
	}

	public void setHigh(double high)
	{
		this.high = high;
	}

	public double getLow()
	{
		return low;
	}

	public void setLow(double low)
	{
		this.low = low;
	}

	public double getClose()
	{
		return close;
	}

	public void setClose(double close)
	{
		this.close = close;
	}

	public long getVolume()
	{
		return volume;
	}

	public void setVolume(long volume)
	{
		this.volume = volume;
	}

	public double getDividendAmount()
	{
		return dividendAmount;
	}

	public void setDividendAmount(double dividendAmount)
	{
		this.dividendAmount = dividendAmount;
	}

	public double getSplitCoefficient()
	{
		return splitCoefficient;
	}

	public void setSplitCoefficient(double splitCoefficient)
	{
		this.splitCoefficient = splitCoefficient;
	}
}
