package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.util.ArrayList;
import java.util.List;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.vparadigm.shared.finance.chart.Chart;

public class ChartVectorMapper
{
	//private static final Instant EPOCH_INSTANT = Instant.f
	
	public static List<ChartVectorDTO> toReverseChartVectorDTOs(Chart chart)
	{
		List<ChartVectorDTO> chartVectorDTOList = null;
		if(chart != null)
		{
			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
			for(int i=0; i<chart.getTimeSeriesSize(); i++)
			{
				chartVectorDTOList.add(new ChartVectorDTO(chart.getReverseIndexTime(i), chart.atReverseIndexMap(i)));
			}
		}
		return chartVectorDTOList;
	}
	
	public static List<ChartVectorDTO> toForwardChartVectorDTOs(Chart chart)
	{
		List<ChartVectorDTO> chartVectorDTOList = null;
		if(chart != null)
		{
			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
			for(int i=0; i<chart.getTimeSeriesSize(); i++)
			{
				chartVectorDTOList.add(new ChartVectorDTO(chart.getForwardIndexTime(i), chart.atForwardIndexMap(i)));
			}
		}
		return chartVectorDTOList;
	}
	
	
}