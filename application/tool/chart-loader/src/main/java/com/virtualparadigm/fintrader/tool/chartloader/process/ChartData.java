package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartVectorVO;

public class ChartData implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonProperty("exchange")
	private String exchange;
	
	@JsonProperty("symbol")
	private String symbol;
	
	@JsonProperty("symbol")
	private SampleDataFrequency sampleDataFrequency;

	@JsonProperty("chartVectors")
	private List<ChartVectorVO> chartVectorDataList;

	public ChartData(String exchange, String symbol, SampleDataFrequency sampleDataFrequency, List<ChartVectorVO> chartVectorDataList)
	{
		super();
		this.exchange = exchange;
		this.symbol = symbol;
		this.sampleDataFrequency = sampleDataFrequency;
		this.chartVectorDataList = chartVectorDataList;
	}

	public String getExchange()
	{
		return exchange;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public SampleDataFrequency getSampleDataFrequency()
	{
		return sampleDataFrequency;
	}

	public List<ChartVectorVO> getChartVectorDataList()
	{
		return chartVectorDataList;
	}

}