package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartDefinitionDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartIdentifier;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartService;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.app.chart.service.api.UserSpaceDTO;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartDelegate;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartVectorVO;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.InstrumentReferenceDelegate;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class ChartLoaderImpl implements ChartLoader
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(ChartLoaderImpl.class);
	private static final String DEFUALT_CHART_NAME = "core";
	private static final String DEFUALT_USERSPACE = "global";
	private static final String FIELD_OPEN = "open";
	private static final String FIELD_HIGH = "high";
	private static final String FIELD_LOW = "low";
	private static final String FIELD_CLOSE = "close";
	private static final String FIELD_VOLUME = "volume";
	private static final String UOM_CURRENCY = "currency/dollar";
	private static final String UOM_NUMSHARES = "numberOfShares";
	
	
	
	@Inject
	private InstrumentReferenceDelegate instrumentRefenceDelegate;
	
	@Inject
	private ChartDelegate instrumentDataDelegate;
	
	@Inject
	private ChartService chartService;
	
	
	public ChartLoaderImpl()
	{
	}
	
	@Override
	public List<InstrumentData> pullInstruments()
	{
		List<InstrumentData> instrumentList = null;
		List<String> exchanges = this.instrumentRefenceDelegate.getExchanges();
		if(exchanges != null)
		{
			instrumentList = new ArrayList<InstrumentData>();
			List<InstrumentData> foundInstruments = null;
			for(String exchange : exchanges)
			{
				foundInstruments = InstrumentDataMapper.toInstrumentDatas(this.instrumentRefenceDelegate.getInstrumentsByExchange(exchange));
				LOGGER.info("found exchange: " + exchange + " with " + ((foundInstruments != null) ? foundInstruments.size() : "0") + " instruments");
				instrumentList.addAll(foundInstruments);
			}
		}
		return instrumentList;
	}

	@Override
	public List<ChartVectorData> pullChart(String exchange, String symbol, SampleDataFrequency sampleDataFrequency, LocalDateTime startTime,
			LocalDateTime endTime)
	{
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotNull("sampleDataFrequency", sampleDataFrequency);
		List<ChartVectorVO> chartVectorVOList =  this.instrumentDataDelegate.getChartVectors(symbol, SampleDTOFrequency.valueOf(sampleDataFrequency.name()), startTime, endTime);
		return ChartVectorDataMapper.toChartVectorDatas(chartVectorVOList);
	}

	@Override
	public void loadChart(String userSpace, String chartName, String exchange, String symbol, SampleDataFrequency sampleDataFrequency, List<ChartVectorData> chartVectorDataList)
	{
		VParadigmValidator.validateNotEmpty("exchange", exchange);
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotNull("sampleDataFrequency", sampleDataFrequency);
		VParadigmValidator.validateNotNull("chartVectorDataList", chartVectorDataList);
		
		if(StringUtils.isEmpty(userSpace))
		{
			userSpace = DEFUALT_USERSPACE;
		}
		
		if(StringUtils.isEmpty(chartName))
		{
			chartName = DEFUALT_CHART_NAME;
		}
		
		SampleDTOFrequency sampleDTOFrequency = SampleDTOFrequency.valueOf(sampleDataFrequency.name());

		UserSpaceDTO userSpaceDTO = this.chartService.saveUserSpace(userSpace);
		if(userSpaceDTO != null)
		{
			ChartDefinitionDTO chartDefinitionDTO = this.chartService.saveChartDefinition(userSpaceDTO.getName(), sampleDTOFrequency, symbol, chartName);
			if(chartDefinitionDTO != null)
			{
				this.chartService.saveChartVectors(
						new ChartIdentifier(userSpaceDTO.getName(), chartDefinitionDTO.getSampleDTOFrequency(), chartDefinitionDTO.getSymbol(), chartDefinitionDTO.getName()),
						ChartVectorDTOMapper.toChartVectorDTOs(chartVectorDataList), 
						ChartVectorDTOMapper.getUnitOfMeasureMapping(), 
						true);
			}
			else
			{
				LOGGER.info("chartDefinitionDTO is null");
			}
		}
		else
		{
			LOGGER.info("userSpaceDTO is null");
		}
		
//		UserSpaceDTO userSpaceDTO = this.chartService.getUserSpace(userSpace);
//		if(userSpaceDTO == null)
//		{
//			userSpaceDTO = this.chartService.saveUserSpace(userSpace);
//		}
//		
//		if(userSpaceDTO != null)
//		{
//			//ChartDefinitionDTO chartDefinitionDTO = this.chartService.saveChartDefinition(userSpace, symbol, sampleDTOFrequency, chartName);
//			ChartDefinitionDTO chartDefinitionDTO = this.chartService.getChartDefinition(userSpace, symbol, sampleDTOFrequency, chartName);
//			if(chartDefinitionDTO == null)
//			{
//				chartDefinitionDTO = this.chartService.saveChartDefinition(userSpace, symbol, sampleDTOFrequency, chartName);
//			}
//			
//			if(chartDefinitionDTO != null)
//			{
//				this.chartService.saveChartVectors(
//						chartDefinitionDTO.getIdentifier(), 
//						ChartVectorDTOMapper.toChartVectorDTOs(chartVectorDataList), 
//						ChartVectorDTOMapper.getUnitOfMeasureMapping(), 
//						true);
//			}
//		}
	}

	@Override
	public List<ChartVectorVO> queryChart(String userSpace, String chartName, String exchange, String symbol, SampleDataFrequency sampleDataFrequency)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args)
	{
		ChartLoaderImpl chartLoader = new ChartLoaderImpl();
		//chartLoader.loadChartData("AAPL", SampleDTOFrequency.DAY, LocalDateTime.of(2000, 1, 1, 1, 0), LocalDateTime.of(2020, 1, 1, 1, 0));
	}

	
	
	
	
//	public List<InstrumentVO> getInstruments()
//	{
//		List<InstrumentVO> instrumentList = null;
//		List<String> exchanges = this.instrumentRefenceDelegate.getExchanges();
//		if(exchanges != null)
//		{
//			instrumentList = new ArrayList<InstrumentVO>();
//			List<InstrumentVO> foundInstruments = null;
//			for(String exchange : exchanges)
//			{
//				foundInstruments = this.instrumentRefenceDelegate.getInstrumentsByExchange(exchange);
//				LOGGER.info("found exchange: " + exchange + " with " + ((foundInstruments != null) ? foundInstruments.size() : "0") + " instruments");
//				instrumentList.addAll(foundInstruments);
//			}
//		}
//		return instrumentList;
//	}
//	
//	public List<ChartVectorVO> getInstrumentData(String exchange, String symbol, SampleDTOFrequency sampleDTOFrequency, LocalDateTime startTime, LocalDateTime endTime)
//	{
//		VParadigmValidator.validateNotEmpty("symbol", symbol);
//		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
//		return this.instrumentDataDelegate.getChartVectors(symbol, sampleDTOFrequency, startTime, endTime);
//	}
//	
//	@Override
//	public void loadInstrumentData(String exchange, String symbol, SampleDTOFrequency sampleDTOFrequency, List<ChartVectorVO> instrumentDataList)
//	{
//		try
//		{
//			List<ChartVectorDTO> chartVectorDTOList = ChartVectorDTOMapper.toChartVectorDTOs(instrumentDataList);
//			if(chartVectorDTOList != null)
//			{
//				LOGGER.trace("processing chartVectorDTOList of size: " + chartVectorDTOList.size());
//				ChartDefinitionDTO chartDefinitionDTO = this.chartService.saveChartDefinition(exchange, symbol, sampleDTOFrequency, DEFUALT_CHART_NAME);
//				if(chartDefinitionDTO != null)
//				{
//					chartService.saveChartVectors(chartDefinitionDTO.getIdentifier(), chartVectorDTOList, ChartVectorDTOMapper.getUnitOfMeasureMapping(), true);
//				}
//			}
//		}
//		catch(Throwable t)
//		{
//			LOGGER.error("error loading instrument", t);
//		}		
//		
//	}
	
	
}