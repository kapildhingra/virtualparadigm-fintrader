package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
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
	
	public static ChartVectorDTO toChartVectorDTO(ChartVectorData chartVectorData)
	{
		ChartVectorDTO chartVectorDTO = null;
		if(chartVectorData != null)
		{
			chartVectorDTO = 
					new ChartVectorDTO(
							new DateTime(chartVectorData.getDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()), 
							chartVectorData.getValues());
		}
		return chartVectorDTO;
	}
	public static List<ChartVectorDTO> toChartVectorDTOs(List<ChartVectorData> chartVectorDataList)
	{
		List<ChartVectorDTO> chartVectorDTOList = null;
		if(chartVectorDataList != null)
		{
			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
			for(ChartVectorData instrumentData : chartVectorDataList)
			{
				chartVectorDTOList.add(ChartVectorDTOMapper.toChartVectorDTO(instrumentData));
			}
		}
		return chartVectorDTOList;
	}
	
	
//	public static ChartVectorDTO toChartVectorDTO(ChartVectorVO chartVectorVO)
//	{
//		ChartVectorDTO chartVectorDTO = null;
//		if(chartVectorVO != null)
//		{
//			Map<String, BigDecimal> valueMap = chartVectorVO.getValues();
//			valueMap.put(Chart.StandardInstrumentSeries.OPEN.name(), chartVectorVO.getOpen());
//			valueMap.put(Chart.StandardInstrumentSeries.HIGH.name(), chartVectorVO.getHigh());
//			valueMap.put(Chart.StandardInstrumentSeries.LOW.name(), chartVectorVO.getLow());
//			valueMap.put(Chart.StandardInstrumentSeries.CLOSE.name(), chartVectorVO.getClose());
//			valueMap.put(Chart.StandardInstrumentSeries.VOLUME.name(), chartVectorVO.getVolume());
//			
//			chartVectorDTO = 
//					new ChartVectorDTO(
//							new DateTime(chartVectorVO.getDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()), 
//							valueMap);
//		}
//		return chartVectorDTO;
//	}
//	
//	public static List<ChartVectorDTO> toChartVectorDTOs(List<ChartVectorVO> chartVectorVOList)
//	{
//		List<ChartVectorDTO> chartVectorDTOList = null;
//		if(chartVectorVOList != null)
//		{
//			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
//			for(ChartVectorVO instrumentData : chartVectorVOList)
//			{
//				chartVectorDTOList.add(ChartVectorDTOMapper.toChartVectorDTO(instrumentData));
//			}
//		}
//		return chartVectorDTOList;
//	}
	
}