package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentData implements Serializable
{
	private static final long serialVersionUID = 1L;
	@JsonProperty("exchange")
	private String exchange;
	@JsonProperty("symbol")
	private String symbol;
	public InstrumentData(String exchange, String symbol)
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