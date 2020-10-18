package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChartCLO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonProperty("mic")
	private String mic;
	
	@JsonProperty("symbol")
	private String symbol;
	
	@JsonProperty("symbol")
	private SampleCLOFrequency sampleDataFrequency;

	@JsonProperty("chartVectors")
	private List<ChartVectorCLO> chartVectorCLOList;

	public ChartCLO(String mic, String symbol, SampleCLOFrequency sampleDataFrequency, List<ChartVectorCLO> chartVectorCLOList)
	{
		super();
		this.mic = mic;
		this.symbol = symbol;
		this.sampleDataFrequency = sampleDataFrequency;
		this.chartVectorCLOList = chartVectorCLOList;
	}

	public String getMic()
	{
		return mic;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public SampleCLOFrequency getSampleDataFrequency()
	{
		return sampleDataFrequency;
	}

	public List<ChartVectorCLO> getChartVectorCLOList()
	{
		return chartVectorCLOList;
	}

}