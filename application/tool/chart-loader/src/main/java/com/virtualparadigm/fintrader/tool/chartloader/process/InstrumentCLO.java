package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentCLO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("mic")
	private String mic;
	
	@JsonProperty("symbol")
	private String symbol;
	
	public InstrumentCLO(String exchange, String symbol)
	{
		super();
		this.mic = exchange;
		this.symbol = symbol;
	}
	public String getMic()
	{
		return mic;
	}
	public String getSymbol()
	{
		return symbol;
	}
}