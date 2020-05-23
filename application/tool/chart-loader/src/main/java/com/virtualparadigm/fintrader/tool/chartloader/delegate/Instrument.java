package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Instrument implements Serializable
{
	private static final long serialVersionUID = 1L;
	@JsonProperty("exchange")
	private String exchange;
	@JsonProperty("symbol")
	private String symbol;
	public Instrument(String exchange, String symbol)
	{
		super();
		this.exchange = exchange;
		this.symbol = symbol;
	}
	public String getExchange()
	{
		return exchange;
	}
	public String getSymbol()
	{
		return symbol;
	}
}