package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ChartRepositoryDAO
{
	void createUserSpace(String userSpace);
	Collection<String> getUserSpaces();
	String getUserSpace(String userSpace);
	void removeUserSpace(String userSpace);
	
	ChartRecord createChart(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName);
	ChartRecord getChart(String userSpace, ChartId chartId);
	ChartRecord getChart(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName);
	Collection<ChartRecord> findCharts(String userSpace);
	Collection<ChartRecord> findCharts(String userSpace, SampleRecordFrequency sampleRecordFrequency);
	Collection<ChartRecord> findCharts(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol);
	
	void saveSampleVector(String userSpace, ChartId chartId, SampleVectorRecord sampleVector);
	void saveSampleVectors(String userSpace, ChartId chartId, List<SampleVectorRecord> sampleVectors);

	void clearSampleVector(String userSpace, ChartId chartId, long sampleTimestamp);
	void clearSample(String userSpace, ChartId chartId, long sampleTimestamp, String field);
	
	List<SampleVectorRecord> getSampleVectors(String userSpace, ChartId chartId);
	List<SampleVectorRecord> getSampleVectors(String userSpace, ChartId chartId, long startTimestamp, long endTimestamp);
	SampleVectorRecord getSampleVector(String userSpace, ChartId chartId, long sampleTimestamp);
	BigDecimal getSample(String userSpace, ChartId chartId, long sampleTimestamp, String field);
	List<BigDecimal> getSamples(String userSpace, ChartId chartId, String field);
	
}