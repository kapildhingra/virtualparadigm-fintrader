package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartDefinitionDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartIdentifier;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartService;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.app.chart.service.api.UserSpaceDTO;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartRecord;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartRepositoryDAO;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleRecord;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleRecordFrequency;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVectorRecord;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;
import com.vparadigm.shared.finance.chart.Chart;
import com.vparadigm.shared.finance.chart.ChartUserSpace;
import com.vparadigm.shared.ts.IDiscreteTimeSeries;
import com.vparadigm.shared.ts.SampleFrequency;

public class ChartServiceImpl implements ChartService
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(ChartServiceImpl.class);
	
	@Inject
	private ChartRepositoryDAO chartRepositoryDAO;


	
	// ==================================================
	// USER SPACE METHODS
	// ==================================================
	@Override
	public UserSpaceDTO getUserSpace(String userSpace)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		
		UserSpaceDTO userSpaceDTO = null;
		String foundUserSpace = this.chartRepositoryDAO.getUserSpace(userSpace);
		if(StringUtils.isNotEmpty(foundUserSpace))
		{
			ChartUserSpace chartUserSpace = ChartUserSpaceMapper.toChartUserSpace(foundUserSpace);
			userSpaceDTO = UserSpaceDTOMapper.toUserSpaceDTO(chartUserSpace);
		}
		return userSpaceDTO;
	}

	@Override
	public Collection<UserSpaceDTO> findUserSpaces()
	{
		Collection<String> foundUserSpaces = this.chartRepositoryDAO.getUserSpaces();
		Collection<ChartUserSpace> chartUserSpaces = ChartUserSpaceMapper.toChartUserSpaces(foundUserSpaces);
		return UserSpaceDTOMapper.toUserSpaceDTOs(chartUserSpaces);
	}

	@Override
	public UserSpaceDTO saveUserSpace(String userSpace)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);

		String foundUserSpace = this.chartRepositoryDAO.getUserSpace(userSpace);
		if(StringUtils.isEmpty(foundUserSpace))
		{
			this.chartRepositoryDAO.createUserSpace(userSpace);
			LOGGER.trace("created userSpace: " + userSpace);
		}
		else
		{
			LOGGER.trace("created userSpace: " + userSpace + " already exists");
		}
		ChartUserSpace chartUserSpace = ChartUserSpaceMapper.toChartUserSpace(userSpace);
		return UserSpaceDTOMapper.toUserSpaceDTO(chartUserSpace);
	}

	@Override
	public void removeUserSpace(String userSpace)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		this.chartRepositoryDAO.removeUserSpace(userSpace);
		LOGGER.trace("removed userSpace: " + userSpace);
	}	
	
	// ==================================================
	// CHART DEFINITION METHODS
	// ==================================================
	@Override
	public ChartDefinitionDTO getChartDefinition(ChartIdentifier chartIdentifier)
	{
		return null;
	}
	
	@Override
	public ChartDefinitionDTO getChartDefinition(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String chartName)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotEmpty("chartName", chartName);
		
		ChartRecord chartRecord = this.chartRepositoryDAO.getChart(userSpace, SampleRecordFrequency.valueOf(sampleDTOFrequency.name()), symbol, chartName);
		Chart chart = ChartMapper.toChart(chartRecord, null);
		return ChartDTOMapper.toChartDefinitionDTO(chart);
	}

	@Override
	public Collection<ChartDefinitionDTO> findChartDefinitions(String userSpace)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		
		Collection<ChartRecord> chartRecords = this.chartRepositoryDAO.findCharts(userSpace);
		Collection<Chart> charts = ChartMapper.toCharts(chartRecords);
		return ChartDTOMapper.toChartDefinitionDTOs(charts);
	}

	@Override
	public Collection<ChartDefinitionDTO> findChartDefinitions(String userSpace, SampleDTOFrequency sampleDTOFrequency)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
		
		Collection<ChartRecord> chartRecords = 
				this.chartRepositoryDAO.findCharts(
						userSpace, 
						SampleRecordFrequency.valueOf(sampleDTOFrequency.name()));
		
		Collection<Chart> charts = ChartMapper.toCharts(chartRecords);
		return ChartDTOMapper.toChartDefinitionDTOs(charts);
	}

	@Override
	public Collection<ChartDefinitionDTO> findChartDefinitions(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		
		Collection<ChartRecord> chartRecords = 
				this.chartRepositoryDAO.findCharts(
						userSpace, 
						SampleRecordFrequency.valueOf(sampleDTOFrequency.name()), 
						symbol);
		
		Collection<Chart> charts = ChartMapper.toCharts(chartRecords);
		return ChartDTOMapper.toChartDefinitionDTOs(charts);
	}			
	
	@Override
	public ChartDefinitionDTO saveChartDefinition(String userSpace, SampleDTOFrequency sampleDTOFrequency, String symbol, String chartName)
	{
		VParadigmValidator.validateNotEmpty("userSpace", userSpace);
		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotEmpty("chartName", chartName);
		
		SampleRecordFrequency sampleRecordFrequency = SampleRecordFrequency.valueOf(sampleDTOFrequency.name());
		
		ChartRecord chartRecord = this.chartRepositoryDAO.getChart(userSpace, sampleRecordFrequency, symbol, chartName);
		if(chartRecord == null)
		{
			chartRecord = this.chartRepositoryDAO.createChart(userSpace, sampleRecordFrequency, symbol, chartName);
			LOGGER.trace("created chartRecord: " + chartRecord.getChartId().asString());
		}
		else
		{
			LOGGER.trace("chartRecord with chartId: " + chartRecord.getChartId().asString() + " already exists");
		}
		
		Chart chart = ChartMapper.toChart(chartRecord, null);
		return ChartDTOMapper.toChartDefinitionDTO(chart);
	}

	@Override
	public void removeChart(ChartIdentifier chartIdentifier)
	{
		// TODO Auto-generated method stub
		
	}

	// ==================================================
	// CHART VECTOR METHODS
	// ==================================================
	@Override
	public ChartVectorDTO getChartVector(ChartIdentifier chartIdentifier, DateTime dateTime)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartDTO getChart(ChartIdentifier chartIdentifier, Interval interval)
	{
		VParadigmValidator.validateNotNull("chartIdentifier", chartIdentifier);
		VParadigmValidator.validateNotNull("interval", interval);
		
		ChartDTO chartDTO = null;
		
		ChartRecord chartRecord = 
				this.chartRepositoryDAO.getChart(
						chartIdentifier.getUserSpace(), 
						SampleRecordFrequency.valueOf(chartIdentifier.getSampleDTOFrequency().name()), 
						chartIdentifier.getSymbol(), 
						chartIdentifier.getChartName());
		
		if(chartRecord != null)
		{
			List<SampleVectorRecord> foundSampleVectorDataList = 
					this.chartRepositoryDAO.getSampleVectors(
							chartIdentifier.getUserSpace(),
							chartRecord.getChartId(), 
							interval.getStartMillis(), 
							interval.getEndMillis());		
			
			Chart chart = ChartMapper.toChart(chartRecord, foundSampleVectorDataList);
			chartDTO = ChartDTOMapper.toChartDTO(chart);
		}
		return chartDTO;
	}
	

	@Override
	public void saveChartVector(ChartIdentifier chartIdentifier, ChartVectorDTO chartVectorDTO, Map<String,String> unitOfMeasureMapping, boolean overwrite)
	{
		List<ChartVectorDTO> chartVectorDTOList = new ArrayList<ChartVectorDTO>();
		chartVectorDTOList.add(chartVectorDTO);
		this.saveChartVectors(chartIdentifier, chartVectorDTOList, unitOfMeasureMapping, overwrite);
	}

	
	@Override
	public void saveChartVectors(ChartIdentifier chartIdentifier, List<ChartVectorDTO> chartVectors, Map<String,String> unitOfMeasureMapping, boolean overwrite)
	{
		VParadigmValidator.validateNotNull("chartIdentifier", chartIdentifier);
		VParadigmValidator.validateNotEmpty("chartVectors", chartVectors);
		
		LOGGER.debug("saving chart vectors of size: " + chartVectors.size());
		
		ChartVectorDTO firstChartVectorDTO = chartVectors.get(0);
		ChartVectorDTO lastChartVectorDTO = chartVectors.get(chartVectors.size()-1);
		
		VParadigmValidator.validateNotNull("firstChartVector.getDateTime", firstChartVectorDTO.getDateTime());
		VParadigmValidator.validateNotNull("lastChartVector.getDateTime", lastChartVectorDTO.getDateTime());
		
		ChartRecord chartRecord = 
				this.chartRepositoryDAO.getChart(
						chartIdentifier.getUserSpace(), 
						SampleRecordFrequency.valueOf(chartIdentifier.getSampleDTOFrequency().name()), 
						chartIdentifier.getSymbol(), 
						chartIdentifier.getChartName());
		
		if(chartRecord != null)
		{
			//Interval chartInterval = new Interval(chartVectors.get(0).getDateTimeMillis(), chartVectors.get(chartVectors.size()-1).getDateTimeMillis());

			List<SampleVectorRecord> foundSampleVectorRecordList = 
					this.chartRepositoryDAO.getSampleVectors(
							chartIdentifier.getUserSpace(), 
							chartRecord.getChartId(), 
							firstChartVectorDTO.getDateTime().getMillis(), 
							lastChartVectorDTO.getDateTime().getMillis());
			
			Interval chartInterval = ChartServiceImpl.getMaxFitInterval(foundSampleVectorRecordList, chartVectors);
			
			ChartUserSpace chartUserSpace = new ChartUserSpace(chartRecord.getUserspace());

			Chart chart = 
					chartUserSpace.createChart(
							SampleFrequency.valueOf(chartRecord.getSampleRecordFrequency().name()), 
							chartRecord.getSymbol(), 
							chartRecord.getChartName(), 
							chartInterval);
			
			chart = ChartServiceImpl.overlaySampleVectorRecords(chart, foundSampleVectorRecordList, true);
			chart = ChartServiceImpl.overlayChartVectorDTOs(chart, chartVectors, unitOfMeasureMapping, false);
			
			this.chartRepositoryDAO.saveSampleVectors(chartIdentifier.getUserSpace(), chartRecord.getChartId(), SampleVectorRecordMapper.toSampleVectorRecords(chart, false));
		}
		else
		{
			throw new IllegalStateException("chartRecord for: " + chartIdentifier + " not found");
		}
	}
	
	// ==================================================
	// UTILITY METHODS
	// ==================================================
	public static Interval getMaxFitInterval(List<SampleVectorRecord> initialSampleVectorList, List<ChartVectorDTO> chartVectorDTOList)
	{
		long intervalStart = 0;
		long intervalEnd = 0;
		if(initialSampleVectorList == null || initialSampleVectorList.size() == 0)
		{
			if(chartVectorDTOList == null || chartVectorDTOList.size() == 0)
			{
				//both null or empty then empty chart
				//throw exception?
			}
			else
			{
				intervalStart = chartVectorDTOList.get(0).getDateTimeMillis();
				intervalEnd = chartVectorDTOList.get(chartVectorDTOList.size()-1).getDateTimeMillis();
			}
		}
		else
		{
			if(chartVectorDTOList == null || chartVectorDTOList.size() == 0)
			{
				//use initial sample vector interval
				intervalStart = initialSampleVectorList.get(0).getGmtTimestamp();
				intervalEnd = initialSampleVectorList.get(initialSampleVectorList.size()-1).getGmtTimestamp();
			}
			else
			{
				//if initial vector start is before overlay, then set interval start to that
				if(initialSampleVectorList.get(0).getGmtTimestamp() <= chartVectorDTOList.get(0).getDateTimeMillis())
				{
					intervalStart = initialSampleVectorList.get(0).getGmtTimestamp();
				}
				else
				{
					intervalStart = chartVectorDTOList.get(0).getDateTimeMillis();
				}
				
				//if initial vector end is after overlay, then set interval end to that
				if(initialSampleVectorList.get(initialSampleVectorList.size()-1).getGmtTimestamp() >= chartVectorDTOList.get(chartVectorDTOList.size()-1).getDateTimeMillis())
				{
					intervalEnd = initialSampleVectorList.get(initialSampleVectorList.size()-1).getGmtTimestamp();
				}
				else
				{
					intervalEnd = chartVectorDTOList.get(chartVectorDTOList.size()-1).getDateTimeMillis();
				}
			}
		}		
		return new Interval(intervalStart, intervalEnd);
	}
	
	public static Chart overlaySampleVectorRecords(Chart chart, List<SampleVectorRecord> sampleVectorRecordList, boolean overwrite)
	{
		Chart resultChart = chart;
		if(resultChart != null && sampleVectorRecordList != null)
		{
			SampleVectorRecord sampleVectorRecord = null;
			SampleRecord[] sampleRecords = null;
			SampleRecord sampleRecord = null;
			DateTime sampleRecordStartDateTime = null;
			IDiscreteTimeSeries<BigDecimal> registeredDTS = null;
			
			if(overwrite)
			{
				if(sampleVectorRecordList != null && sampleVectorRecordList.size() > 0)
				{
					
					for(int sampleVectorRecordIndex=0; sampleVectorRecordIndex<sampleVectorRecordList.size(); sampleVectorRecordIndex++)
					{
						sampleVectorRecord = sampleVectorRecordList.get(sampleVectorRecordIndex);
						if(sampleVectorRecord != null)
						{
							sampleRecords = sampleVectorRecord.getSampleRecords();
							if(sampleRecords != null)
							{
								for(int sampleRecordIndex=0; sampleRecordIndex<sampleRecords.length; sampleRecordIndex++)
								{
									sampleRecord = sampleRecords[sampleRecordIndex];
									registeredDTS = resultChart.getRegisteredDiscreteTimeSeries(sampleRecord.getField());
									if(registeredDTS == null)
									{
										registeredDTS = resultChart.createRegisteredDiscreteTimeSeries(sampleRecord.getField(), sampleRecord.getUnitOfMeasure(), null);
									}
									//setSample() instead of addMeasurement() since we either want to set the value or ignore it. No running average in this case.
									sampleRecordStartDateTime = new DateTime(sampleVectorRecord.getGmtTimestamp());
									registeredDTS.clearSample(sampleRecordStartDateTime);
									registeredDTS.setSample(sampleRecordStartDateTime, sampleRecord.getValue());
								}
							}
						}
					}				
				}				
			}
			else
			{
				if(sampleVectorRecordList != null && sampleVectorRecordList.size() > 0)
				{
					BigDecimal sample = null;
					for(int sampleVectorRecordIndex=0; sampleVectorRecordIndex<sampleVectorRecordList.size(); sampleVectorRecordIndex++)
					{
						sampleVectorRecord = sampleVectorRecordList.get(sampleVectorRecordIndex);
						if(sampleVectorRecord != null)
						{
							sampleRecords = sampleVectorRecord.getSampleRecords();
							if(sampleRecords != null)
							{
								for(int sampleRecordIndex=0; sampleRecordIndex<sampleRecords.length; sampleRecordIndex++)
								{
									sampleRecord = sampleRecords[sampleRecordIndex];
									registeredDTS = resultChart.getRegisteredDiscreteTimeSeries(sampleRecord.getField());
									if(registeredDTS == null)
									{
										registeredDTS = resultChart.createRegisteredDiscreteTimeSeries(sampleRecord.getField(), sampleRecord.getUnitOfMeasure(), null);
									}
									sampleRecordStartDateTime = new DateTime(sampleVectorRecord.getGmtTimestamp());
									sample = registeredDTS.atTime(sampleRecordStartDateTime);
									if(sample == null)
									{
										registeredDTS.setSample(sampleRecordStartDateTime, sampleRecord.getValue());
									}
								}
							}
						}
					}				
				}
			}
		}
		return resultChart;
	}	
	
	public static Chart overlayChartVectorDTOs(Chart chart, List<ChartVectorDTO> chartVectors, Map<String,String> unitOfMeasureMapping, boolean overwrite)
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
							registeredDTS.clearSample(chartVectorDTO.getDateTime());
							registeredDTS.addMeasurement(chartVectorDTO.getDateTime(), valueMap.get(dtsName));
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
							registeredDTS.addMeasurement(chartVectorDTO.getDateTime(), valueMap.get(dtsName));
						}
					}
				}
			}
		}
		return resultChart;
	}




	
	
	
}