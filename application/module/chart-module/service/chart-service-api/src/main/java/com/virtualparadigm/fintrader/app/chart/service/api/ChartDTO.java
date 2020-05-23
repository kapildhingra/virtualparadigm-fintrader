package com.virtualparadigm.fintrader.app.chart.service.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ChartDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ChartDefinitionDTO chartDefinition;
	private Map<String, String> unitOfMeasureMapping;
	private List<ChartVectorDTO> chartVectors;
	
	public ChartDTO()
	{
		super();
	}
	public ChartDTO(ChartDefinitionDTO chartDefinition, Map<String, String> unitOfMeasureMapping, List<ChartVectorDTO> chartVectors)
	{
		super();
		this.chartDefinition = chartDefinition;
		this.unitOfMeasureMapping = unitOfMeasureMapping;
		this.chartVectors = chartVectors;
	}

	public ChartDefinitionDTO getChartDefinition()
	{
		return chartDefinition;
	}
	public void setChartDefinition(ChartDefinitionDTO chartDefinition)
	{
		this.chartDefinition = chartDefinition;
	}
	public Map<String, String> getUnitOfMeasureMapping()
	{
		return unitOfMeasureMapping;
	}
	public void setUnitOfMeasureMapping(Map<String, String> unitOfMeasureMapping)
	{
		this.unitOfMeasureMapping = unitOfMeasureMapping;
	}
	public List<ChartVectorDTO> getChartVectors()
	{
		return chartVectors;
	}
	public void setChartVectors(List<ChartVectorDTO> chartVectors)
	{
		this.chartVectors = chartVectors;
	}

}