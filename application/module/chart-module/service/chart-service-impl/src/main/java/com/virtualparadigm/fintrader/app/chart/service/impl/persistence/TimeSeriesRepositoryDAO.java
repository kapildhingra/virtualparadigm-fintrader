package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.vparadigm.shared.comp.common.identity.GUID;

public interface TimeSeriesRepositoryDAO
{
	AggregateTimeSeries saveAggregateTimeSeries(String name, SampleFrequency sampleFrequency);
	AggregateTimeSeries getAggregateTimeSeries(GUID aggregateTimeSeriesGUID);
	Collection<AggregateTimeSeries> findAggregateTimeSeries();
	
	boolean saveSampleVector(AggregateTimeSeries aggregateTimeSeries, SampleVector sampleVector);
	boolean saveSampleVectors(AggregateTimeSeries aggregateTimeSeries, List<SampleVector> sampleVectors);

	boolean clearSampleVector(long sampleTimestamp);
	boolean clearSample(long sampleTimestamp, String field);
	
	List<SampleVector> getSampleVectors(GUID aggregateTimeSeriesGUID);
	SampleVector getSampleVector(GUID aggregateTimeSeriesGUID, long sampleTimestamp);
	BigDecimal getSample(GUID aggregateTimeSeriesGUID, long sampleTimestamp, String field);
	List<BigDecimal> getSamples(GUID aggregateTimeSeriesGUID, String field);
	
}