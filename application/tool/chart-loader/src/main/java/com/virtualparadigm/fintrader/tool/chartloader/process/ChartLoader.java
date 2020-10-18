package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.LocalDateTime;
import java.util.List;

public interface ChartLoader
{
	// =====================================
	// PULL
	// =====================================
	List<MarketCLO> pullMarkets();
	List<InstrumentCLO> pullInstruments();
	ChartCLO pullChart(String exchange, String symbol, SampleCLOFrequency sampleDataFrequency, LocalDateTime startTime, LocalDateTime endTime);


	// =====================================
	// CACHE
	// =====================================
//	void cacheInstruments(List<InstrumentData> instrumentDataList);
//	void cacheChart(String exchange, String symbol, SampleDTOFrequency sampleDTOFrequency, List<ChartVectorVO> chartVectorDataList);
	
	
	// =====================================
	// LOAD
	// =====================================
	void loadChart(String userSpace, String chartName, String exchange, String symbol, SampleCLOFrequency sampleDataFrequency, List<ChartVectorCLO> chartVectorDataList);
	
	
	// =====================================
	// QUERY
	// =====================================
	List<ChartVectorCLO> queryChart(String userSpace, String chartName, String exchange, String symbol, SampleCLOFrequency sampleDataFrequency);
	
	
}