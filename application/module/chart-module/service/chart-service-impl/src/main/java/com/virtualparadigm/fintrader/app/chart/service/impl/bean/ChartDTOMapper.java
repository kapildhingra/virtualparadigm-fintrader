package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartDefinitionDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.vparadigm.shared.finance.chart.Chart;
import com.vparadigm.shared.ts.IDiscreteTimeSeries;
import com.vparadigm.shared.ts.SampleFrequency;

public class ChartDTOMapper
{
	public static ChartDTO toChartDTO(Chart chart)
	{
		ChartDTO chartDTO = null;
		if(chart != null)
		{
			Map<String, String> unitOfMeasureMapping = new HashMap<String, String>();
			if(chart.getIndexedDTSNames() != null)
			{
				IDiscreteTimeSeries<BigDecimal> discreteTimeSeries = null;
				for(String dtsName : chart.getIndexedDTSNames())
				{
					discreteTimeSeries = chart.getRegisteredDiscreteTimeSeries(dtsName);
					if(discreteTimeSeries != null)
					{
						unitOfMeasureMapping.put(dtsName, discreteTimeSeries.getUnitOfMeasure());
					}
				}
			}
			
			chartDTO = 
					new ChartDTO(
							ChartDTOMapper.toChartDefinitionDTO(chart),
							unitOfMeasureMapping,
							ChartDTOMapper.toReverseChartVectorDTOs(chart));
		}
		return chartDTO;
	}
	
	public static ChartDefinitionDTO toChartDefinitionDTO(Chart chart)
	{
		ChartDefinitionDTO chartDefinitionDTO = null;
		if(chart != null && chart.getUserSpace() != null && chart.getGUID() != null)
		{
			chartDefinitionDTO = 
					new ChartDefinitionDTO(
							chart.getUserSpace().getName(), 
							ChartDTOMapper.getSampleDTOFrequency(chart.getSampleFrequency()), 
							chart.getSymbol(), 
							chart.getChartName());
		}
		return chartDefinitionDTO;
	}
	
	public static Collection<ChartDefinitionDTO> toChartDefinitionDTOs(Collection<Chart> charts)
	{
		List<ChartDefinitionDTO> chartDefinitionDTOList = null;
		if(charts != null)
		{
			chartDefinitionDTOList = new ArrayList<ChartDefinitionDTO>();
			for(Chart chart : charts)
			{
				chartDefinitionDTOList.add(ChartDTOMapper.toChartDefinitionDTO(chart));
			}
		}
		return chartDefinitionDTOList;
	}
	
	public static SampleDTOFrequency getSampleDTOFrequency(SampleFrequency sampleFrequency)
	{
		SampleDTOFrequency sampleDTOFrequency = null;
		if(sampleFrequency != null)
		{
			sampleDTOFrequency = SampleDTOFrequency.valueOf(sampleFrequency.name());
		}
		return sampleDTOFrequency;
	}
	
	public static List<ChartVectorDTO> toReverseChartVectorDTOs(Chart chart)
	{
		List<ChartVectorDTO> chartVectorDTOList = null;
		if(chart != null)
		{
			chartVectorDTOList = new ArrayList<ChartVectorDTO>();
			for(int i=0; i<chart.getTimeSeriesSize(); i++)
			{
				chartVectorDTOList.add(new ChartVectorDTO(chart.getReverseIndexTime(i), chart.atReverseIndexMap(i, false)));
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
				chartVectorDTOList.add(new ChartVectorDTO(chart.getForwardIndexTime(i), chart.atForwardIndexMap(i, false)));
			}
		}
		return chartVectorDTOList;
	}
	
	
}