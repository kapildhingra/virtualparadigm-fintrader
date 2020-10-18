package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.vparadigm.shared.finance.ts.Chart;

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
	
	public static ChartVectorDTO toChartVectorDTO(ChartVectorCLO chartVectorCLO)
	{
		ChartVectorDTO chartVectorDTO = null;
		if(chartVectorCLO != null)
		{
			chartVectorDTO = 
					new ChartVectorDTO(
							new DateTime(chartVectorCLO.getDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()), 
							chartVectorCLO.getValues());
		}
		return chartVectorDTO;
	}
	public static List<ChartVectorDTO> toChartVectorDTOs(List<ChartVectorCLO> chartVectorDataList)
	{
		List<ChartVectorDTO> chartVectorDTOList = null;
		if(chartVectorDataList != null)
		{
			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
			for(ChartVectorCLO instrumentData : chartVectorDataList)
			{
				chartVectorDTOList.add(ChartVectorDTOMapper.toChartVectorDTO(instrumentData));
			}
		}
		return chartVectorDTOList;
	}
	
	
	
}