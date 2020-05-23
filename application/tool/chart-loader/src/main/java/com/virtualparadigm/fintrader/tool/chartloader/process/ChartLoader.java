package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.LocalDateTime;
import java.util.List;

import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartData;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.Instrument;

public interface ChartLoader
{
	List<Instrument> getInstruments();
	
	List<ChartData> getChartData(String symbol, SampleDTOFrequency sampleDTOFrequency, LocalDateTime startTime, LocalDateTime endTime);
	
	void loadChartData(String symbol, SampleDTOFrequency sampleDTOFrequency, List<ChartData> chartDataList);
}