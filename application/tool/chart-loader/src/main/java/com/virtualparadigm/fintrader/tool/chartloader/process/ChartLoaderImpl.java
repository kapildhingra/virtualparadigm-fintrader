package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartDefinitionDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartService;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartVectorDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartData;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartDelegate;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartReferenceDelegate;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.Instrument;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class ChartLoaderImpl implements ChartLoader
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(ChartLoaderImpl.class);
	private static final String FIELD_OPEN = "open";
	private static final String FIELD_HIGH = "high";
	private static final String FIELD_LOW = "low";
	private static final String FIELD_CLOSE = "close";
	private static final String FIELD_VOLUME = "volume";
	private static final String UOM_CURRENCY = "currency/dollar";
	private static final String UOM_NUMSHARES = "numberOfShares";
	
	@Inject
	private ChartReferenceDelegate instrumentRefenceDelegate;
	
	@Inject
	private ChartDelegate instrumentChartDelegate;
	
	@Inject
	private ChartService chartService;
	
	
	public ChartLoaderImpl()
	{
	}
	
	public static void main(String[] args)
	{
		ChartLoaderImpl chartLoader = new ChartLoaderImpl();
		//chartLoader.loadChartData("AAPL", SampleDTOFrequency.DAY, LocalDateTime.of(2000, 1, 1, 1, 0), LocalDateTime.of(2020, 1, 1, 1, 0));
	}
	
	
	public List<Instrument> getInstruments()
	{
		List<Instrument> instrumentList = null;
		List<String> exchanges = this.instrumentRefenceDelegate.getExchanges();
		if(exchanges != null)
		{
			instrumentList = new ArrayList<Instrument>();
			List<Instrument> foundInstruments = null;
			for(String exchange : exchanges)
			{
				foundInstruments = this.instrumentRefenceDelegate.getInstrumentsByExchange(exchange);
				LOGGER.info("found exchange: " + exchange + " with " + ((foundInstruments != null) ? foundInstruments.size() : "0") + " instruments");
				instrumentList.addAll(foundInstruments);
			}
		}
		return instrumentList;
	}
	
	public List<ChartData> getChartData(String symbol, SampleDTOFrequency sampleDTOFrequency, LocalDateTime startTime, LocalDateTime endTime)
	{
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
		return this.instrumentChartDelegate.getInstrumentData(symbol, sampleDTOFrequency, startTime, endTime);
	}
	
	
	
	

	@Override
	public void loadChartData(String symbol, SampleDTOFrequency sampleDTOFrequency, List<ChartData> chartDataList)
	{
		try
		{
			List<ChartVectorDTO> chartVectorDTOList = ChartVectorDTOMapper.toChartVectorDTOs(chartDataList);
			if(chartVectorDTOList != null)
			{
				ChartDefinitionDTO chartDefinitionDTO = this.chartService.saveChartDefinition(symbol, sampleDTOFrequency, "core");
				if(chartDefinitionDTO != null)
				{
					chartService.saveChartVectors(chartDefinitionDTO.getIdentifier(), chartVectorDTOList, ChartVectorDTOMapper.getUnitOfMeasureMapping(), true);
				}
			}
		}
		catch(Throwable t)
		{
			LOGGER.error("error loading instrument", t);
		}		
		
	}
	
	
}