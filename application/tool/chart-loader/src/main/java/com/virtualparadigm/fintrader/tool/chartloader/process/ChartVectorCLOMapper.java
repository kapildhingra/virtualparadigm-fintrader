package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartVectorVO;
import com.vparadigm.shared.finance.ts.Chart;

public class ChartVectorCLOMapper
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
		return ChartVectorCLOMapper.unitOfMeasureMapping;
	}
	
	public static ChartVectorCLO toChartVectorCLO(ChartVectorVO chartVectorVO)
	{
		ChartVectorCLO chartVectorData = null;
		if(chartVectorVO != null)
		{
			chartVectorData = 
					new ChartVectorCLO(
							chartVectorVO.getDateTime(),
							chartVectorVO.getValues());
		}
		return chartVectorData;
	}
	
	public static List<ChartVectorCLO> toChartVectorCLOs(List<ChartVectorVO> chartVectorVOList)
	{
		List<ChartVectorCLO> chartVectorDataList = null;
		if(chartVectorVOList != null)
		{
			chartVectorDataList = new ArrayList<ChartVectorCLO>();
			for(ChartVectorVO instrumentData : chartVectorVOList)
			{
				chartVectorDataList.add(ChartVectorCLOMapper.toChartVectorCLO(instrumentData));
			}
		}
		return chartVectorDataList;
	}
	
}