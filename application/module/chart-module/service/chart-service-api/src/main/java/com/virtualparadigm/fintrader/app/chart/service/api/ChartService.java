package com.virtualparadigm.fintrader.app.chart.service.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public interface ChartService
{
	// ======================================
	// User Spaces
	// ======================================
	UserSpaceDTO getUserSpace(String userSpace);
	Collection<UserSpaceDTO> findUserSpaces();
	UserSpaceDTO saveUserSpace(String userSpace);
	void removeUserSpace(String userSpace);
	
	// ======================================
	// Chart Vectors
	// ======================================
	ChartDefinitionDTO getChartDefinition(ChartIdentifier chartIdentifier);
	ChartDefinitionDTO getChartDefinition(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String chartName);
	Collection<ChartDefinitionDTO> findChartDefinitions(String userSpace);
	Collection<ChartDefinitionDTO> findChartDefinitions(String userSpace, SampleDTOFrequency sampleDTOFrequency);
	Collection<ChartDefinitionDTO> findChartDefinitions(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol);
	ChartDefinitionDTO saveChartDefinition(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String chartName);
	
	void removeChart(ChartIdentifier chartIdentifier);
	
	// ======================================
	// Chart Vectors
	// ======================================
	ChartVectorDTO getChartVector(ChartIdentifier chartIdentifier, DateTime dateTime);
	void saveChartVector(ChartIdentifier chartIdentifier, ChartVectorDTO chartVectorDTO, Map<String, String> unitOfMeasureMapping, boolean overwrite);

	ChartDTO getChart(ChartIdentifier chartIdentifier, Interval interval);
	void saveChartVectors(ChartIdentifier chartIdentifier, List<ChartVectorDTO> chartVectors, Map<String, String> unitOfMeasureMapping, boolean overwrite);
}