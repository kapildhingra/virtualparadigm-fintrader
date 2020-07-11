package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.time.LocalDateTime;
import java.util.List;

import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;

public interface ChartDelegate
{
	List<ChartVectorVO> getChartVectors(String symbol, SampleDTOFrequency sampleDTOFrequency, LocalDateTime startTime, LocalDateTime endTime);
}