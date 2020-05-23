package com.virtualparadigm.fintrader.app.chart.service.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public interface ChartService
{
	// ======================================
	// Chart Vectors
	// ======================================
	ChartDefinitionDTO getChartDefinition(String chartIdentifier);
	ChartDefinitionDTO getChartDefinition(String symbol, SampleDTOFrequency sampleDTOFrequency, String chartName);
	Collection<ChartDefinitionDTO> findChartDefinitions();
	ChartDefinitionDTO saveChartDefinition(String symbol, SampleDTOFrequency sampleDTOFrequency, String name);
	
	void removeChart(String chartIdentifier);
	
	// ======================================
	// Chart Vectors
	// ======================================
	ChartVectorDTO getChartVector(String chartIdentifier, DateTime dateTime);
	void saveChartVector(String chartIdentifier, ChartVectorDTO chartVectorDTO, Map<String, String> unitOfMeasureMapping, boolean overwrite);

	ChartDTO getChart(String chartIdentifier, Interval interval);
	void saveChartVectors(String chartIdentifier, List<ChartVectorDTO> chartVectors, Map<String, String> unitOfMeasureMapping, boolean overwrite);
}