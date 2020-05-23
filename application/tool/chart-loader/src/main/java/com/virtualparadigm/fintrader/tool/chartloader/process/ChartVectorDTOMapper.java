package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartData;
import com.vparadigm.shared.finance.chart.Chart;

public class ChartVectorDTOMapper
{
	private static final String UNIT_OF_MEASURE_DOLLAR = "currency/dollar";
	private static final String UNIT_OF_MEASURE_COUNT = "count";
	
	private static Map<String, String> unitOfMeasureMapping = new HashMap<String, String>();
	static
	{
		unitOfMeasureMapping.put(Chart.StandardInstrumentSeries.OPEN.name(), UNIT_OF_MEASURE_DOLLAR);
		unitOfMeasureMapping.put(Chart.StandardInstrumentSeries.HIGH.name(), UNIT_OF_MEASURE_DOLLAR);
		unitOfMeasureMapping.put(Chart.StandardInstrumentSeries.LOW.name(), UNIT_OF_MEASURE_DOLLAR);
		unitOfMeasureMapping.put(Chart.StandardInstrumentSeries.CLOSE.name(), UNIT_OF_MEASURE_DOLLAR);
		unitOfMeasureMapping.put(Chart.StandardInstrumentSeries.VOLUME.name(), UNIT_OF_MEASURE_COUNT);
	}
	
	public static Map<String, String> getUnitOfMeasureMapping()
	{
		return ChartVectorDTOMapper.unitOfMeasureMapping;
	}
	
	public static ChartVectorDTO toChartVectorDTO(ChartData instrumentData)
	{
		ChartVectorDTO chartVectorDTO = null;
		if(instrumentData != null)
		{
			Map<String, BigDecimal> valueMap = new HashMap<String, BigDecimal>();
			valueMap.put(Chart.StandardInstrumentSeries.OPEN.name(), BigDecimal.valueOf(instrumentData.getOpen()));
			valueMap.put(Chart.StandardInstrumentSeries.HIGH.name(), BigDecimal.valueOf(instrumentData.getHigh()));
			valueMap.put(Chart.StandardInstrumentSeries.LOW.name(), BigDecimal.valueOf(instrumentData.getLow()));
			valueMap.put(Chart.StandardInstrumentSeries.CLOSE.name(), BigDecimal.valueOf(instrumentData.getClose()));
			valueMap.put(Chart.StandardInstrumentSeries.VOLUME.name(), BigDecimal.valueOf(instrumentData.getVolume()));
			
			chartVectorDTO = 
					new ChartVectorDTO(
							new DateTime(instrumentData.getDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()), 
							valueMap);
		}
		return chartVectorDTO;
	}
	
	public static List<ChartVectorDTO> toChartVectorDTOs(List<ChartData> instrumentDataList)
	{
		List<ChartVectorDTO> chartVectorDTOList = null;
		if(instrumentDataList != null)
		{
			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
			for(ChartData instrumentData : instrumentDataList)
			{
				chartVectorDTOList.add(ChartVectorDTOMapper.toChartVectorDTO(instrumentData));
			}
		}
		return chartVectorDTOList;
	}
	
}