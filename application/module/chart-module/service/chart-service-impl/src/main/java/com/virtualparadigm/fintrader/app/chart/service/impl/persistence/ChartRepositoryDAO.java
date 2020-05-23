package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ChartRepositoryDAO
{
	ChartData createChart(String userSpace, SampleFrequencyData sampleFrequency, String symbol, String chartName);
	ChartData getChart(ChartId chartId);
	ChartData getChart(String userSpace, SampleFrequencyData sampleFrequency, String symbol, String chartName);
	Collection<ChartData> findCharts(String userSpace);
	Collection<ChartData> findCharts(String userSpace, SampleFrequencyData sampleFrequency);
	Collection<ChartData> findCharts(String userSpace, SampleFrequencyData sampleFrequency, String symbol);
	
	void saveSampleVector(ChartId chartId, SampleVectorData sampleVector);
	void saveSampleVectors(ChartId chartId, List<SampleVectorData> sampleVectors);

	void clearSampleVector(ChartId chartId, long sampleTimestamp);
	void clearSample(ChartId chartId, long sampleTimestamp, String field);
	
	List<SampleVectorData> getSampleVectors(ChartId chartId);
	List<SampleVectorData> getSampleVectors(ChartId chartId, long startTimestamp, long endTimestamp);
	SampleVectorData getSampleVector(ChartId chartId, long sampleTimestamp);
	BigDecimal getSample(ChartId chartId, long sampleTimestamp, String field);
	List<BigDecimal> getSamples(ChartId chartId, String field);
	
}