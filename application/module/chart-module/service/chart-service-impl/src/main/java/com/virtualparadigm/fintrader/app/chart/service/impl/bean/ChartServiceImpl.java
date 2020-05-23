package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartDefinitionDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartService;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartData;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartId;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartRepositoryDAO;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVectorData;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;
import com.vparadigm.shared.finance.chart.Chart;
import com.vparadigm.shared.ts.IDiscreteTimeSeries;

public class ChartServiceImpl implements ChartService
{
	private static final VParadigmLogger logger = new VParadigmLogger(ChartServiceImpl.class);
	
	@Inject
	private ChartRepositoryDAO chartRepositoryDAO;

	@Override
	public ChartDefinitionDTO getChartDefinition(String chartIdentifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartDefinitionDTO getChartDefinition(String symbol, SampleDTOFrequency sampleDTOFrequency, String chartName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartDefinitionDTO> findChartDefinitions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartDefinitionDTO saveChartDefinition(String symbol, SampleDTOFrequency sampleDTOFrequency, String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChart(String chartIdentifier)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChartVectorDTO getChartVector(String chartIdentifier, DateTime dateTime)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartDTO getChart(String chartIdentifier, Interval interval)
	{
		VParadigmValidator.validateNotNull("chartIdentifier", chartIdentifier);
		VParadigmValidator.validateNotNull("interval", interval);
		
		ChartDTO chartDTO = null;
		
		ChartData chartData = this.chartRepositoryDAO.getChart(new ChartId(chartIdentifier));
		if(chartData != null)
		{
			List<SampleVectorData> foundSampleVectorDataList = 
					this.chartRepositoryDAO.getSampleVectors(
							chartData.getChartId(), 
							interval.getStartMillis(), 
							interval.getEndMillis());		
			
			Chart chart = ChartMapper.toChart(chartData, foundSampleVectorDataList);
			chartDTO = ChartDTOMapper.toChartDTO(chart);
		}
		return chartDTO;
	}
	

	@Override
	public void saveChartVector(String chartIdentifier, ChartVectorDTO chartVectorDTO, Map<String,String> unitOfMeasureMapping, boolean overwrite)
	{
		List<ChartVectorDTO> chartVectorDTOList = new ArrayList<ChartVectorDTO>();
		chartVectorDTOList.add(chartVectorDTO);
		this.saveChartVectors(chartIdentifier, chartVectorDTOList, unitOfMeasureMapping, overwrite);
	}

	
	@Override
	public void saveChartVectors(String chartIdentifier, List<ChartVectorDTO> chartVectors, Map<String,String> unitOfMeasureMapping, boolean overwrite)
	{
		VParadigmValidator.validateNotNull("chartIdentifier", chartIdentifier);
		VParadigmValidator.validateNotEmpty("chartVectors", chartVectors);
		
		ChartVectorDTO firstChartVectorDTO = chartVectors.get(0);
		ChartVectorDTO lastChartVectorDTO = chartVectors.get(chartVectors.size()-1);
		
		VParadigmValidator.validateNotNull("firstChartVector.getDateTime", firstChartVectorDTO.getDateTIme());
		VParadigmValidator.validateNotNull("lastChartVector.getDateTime", lastChartVectorDTO.getDateTIme());
		
		ChartData chartData = this.chartRepositoryDAO.getChart(new ChartId(chartIdentifier));
		if(chartData != null)
		{
			List<SampleVectorData> foundSampleVectorDataList = 
					this.chartRepositoryDAO.getSampleVectors(
							chartData.getChartId(), 
							firstChartVectorDTO.getDateTIme().getMillis(), 
							lastChartVectorDTO.getDateTIme().getMillis());
			
			Chart chart = ChartMapper.toChart(chartData, foundSampleVectorDataList);
			
			if(chart != null)
			{
				chart = ChartServiceImpl.overlayChartVectors(chart, chartVectors, unitOfMeasureMapping, overwrite);
			}
			
			this.chartRepositoryDAO.saveSampleVectors(chartData.getChartId(), SampleVectorMapper.toSampleVectorDatas(chart));
			
		}
	}
	
	
	
	public static Chart overlayChartVectors(Chart chart, List<ChartVectorDTO> chartVectors, Map<String,String> unitOfMeasureMapping, boolean overwrite)
	{
		Chart resultChart = chart;
		if(resultChart != null && chartVectors != null && unitOfMeasureMapping != null)
		{
			Map<String, BigDecimal> valueMap = null;
			IDiscreteTimeSeries<BigDecimal> registeredDTS = null;
			if(overwrite)
			{
				for(ChartVectorDTO chartVectorDTO : chartVectors)
				{
					valueMap = chartVectorDTO.getValueMap();
					if(valueMap != null)
					{
						for(String dtsName : valueMap.keySet())
						{
							registeredDTS = chart.getRegisteredDiscreteTimeSeries(dtsName);
							if(registeredDTS == null)
							{
								//Should see NPE if unitOfMeasure is not present for timeseries
								registeredDTS = chart.createRegisteredDiscreteTimeSeries(dtsName, unitOfMeasureMapping.get(dtsName), null);
							}
							//if overwrite, then clear sample first then set it
							registeredDTS.clearSample(chartVectorDTO.getDateTIme());
							registeredDTS.addMeasurement(chartVectorDTO.getDateTIme(), valueMap.get(dtsName));
						}
					}
				}
			}
			else
			{
				for(ChartVectorDTO chartVectorDTO : chartVectors)
				{
					valueMap = chartVectorDTO.getValueMap();
					if(valueMap != null)
					{
						for(String dtsName : valueMap.keySet())
						{
							registeredDTS = chart.getRegisteredDiscreteTimeSeries(dtsName);
							if(registeredDTS == null)
							{
								registeredDTS = chart.createRegisteredDiscreteTimeSeries(dtsName, unitOfMeasureMapping.get(dtsName), null);
							}
							registeredDTS.addMeasurement(chartVectorDTO.getDateTIme(), valueMap.get(dtsName));
						}
					}
				}
			}
		}
		return resultChart;
	}
	
}