package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.DailyAdjusted;
import org.patriques.output.timeseries.data.StockData;

import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.tool.chartloader.config.ChartLoaderConfiguration;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class ChartDelegateAlphaVantageImpl implements ChartDelegate
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(ChartDelegateAlphaVantageImpl.class);
	
	private AlphaVantageConnector apiConnector;
	private TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

	public ChartDelegateAlphaVantageImpl()
	{
		this(ChartLoaderConfiguration.getAlphaVantageAPIKey(), ChartLoaderConfiguration.getAlphaVantageAPITimeoutMillis());
	}
	public ChartDelegateAlphaVantageImpl(String alphaVantageAPIKey, int alphaVantageTimeout)
	{
		try
		{
			this.apiConnector = new AlphaVantageConnector(alphaVantageAPIKey, alphaVantageTimeout);
			this.stockTimeSeries = new TimeSeries(apiConnector);
		}
		catch(Throwable t)
		{
			LOGGER.error("error initializing", t);
		}
	}
	

	@Override
	public List<ChartData> getInstrumentData(String symbol, SampleDTOFrequency sampleDTOFrequency, LocalDateTime startTime, LocalDateTime endTime)
	{
		List<ChartData> instrumentDataList = null;
		List<StockData> stockDataList = this.retrieveStockData(symbol, sampleDTOFrequency);
		if(stockDataList != null)
		{
			instrumentDataList = new ArrayList<ChartData>();
			for(StockData stockData : stockDataList)
			{
				if(stockData.getDateTime() != null)
				{
					if((startTime == null || stockData.getDateTime().compareTo(startTime) >= 0) && (endTime == null || stockData.getDateTime().compareTo(endTime) <= 0))
					{
						instrumentDataList.add(ChartDataMapper.map(stockData));
					}
				}
				else
				{
					LOGGER.warning("stockData dateTime is null. cannot load this record");
				}
			}
		}
		return instrumentDataList;
	}
	
	
	private List<StockData> retrieveStockData(String symbol, SampleDTOFrequency sampleDTOFrequency)
	{
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotNull("sampleDTOFrequency", sampleDTOFrequency);
		
		List<StockData> stockDataList = null;
		try
		{
			DailyAdjusted response = null;
			switch(sampleDTOFrequency)
			{
				case DAY:
				{
					response = this.stockTimeSeries.dailyAdjusted(symbol, OutputSize.FULL);
					break;
				}
				default:
				{
					throw new UnsupportedOperationException(sampleDTOFrequency.name() + " frequency is not supported");
				}
			}
			if(response != null)
			{
				Map<String, String> metaData = response.getMetaData();
				LOGGER.info("Fetched Instrument: " + metaData.get("2. Symbol"));
				LOGGER.info("Additiona Information: " + metaData.get("1. Information"));
				stockDataList = response.getStockData();
			}
		}
		catch(AlphaVantageException e)
		{
			LOGGER.error("error retreiving stock data", e);
		}		
		return stockDataList;
	}

}