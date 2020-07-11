package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.LocalDateTime;
import java.util.List;

import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartVectorVO;

public interface ChartLoader
{
	List<InstrumentData> pullInstruments();
	List<ChartVectorData> pullChart(String exchange, String symbol, SampleDataFrequency sampleDataFrequency, LocalDateTime startTime, LocalDateTime endTime);


//	void cacheInstruments(List<InstrumentData> instrumentDataList);
//	void cacheChart(String exchange, String symbol, SampleDTOFrequency sampleDTOFrequency, List<ChartVectorVO> chartVectorDataList);
	
	void loadChart(String userSpace, String chartName, String exchange, String symbol, SampleDataFrequency sampleDataFrequency, List<ChartVectorData> chartVectorDataList);
	
	List<ChartVectorVO> queryChart(String userSpace, String chartName, String exchange, String symbol, SampleDataFrequency sampleDataFrequency);
	
	
}