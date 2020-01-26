package com.virtualparadigm.fintrader.tool.instrloader.process;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.Daily;
import org.patriques.output.timeseries.data.StockData;

import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.AggregateTimeSeries;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleData;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleFrequency;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVector;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.TimeSeriesRepositoryDAO;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.TimeSeriesRepositoryDAOInfluxImpl;

public class InstrumentLoader
{
	private static final String RETENTION_POLICY_AUTOGEN = "autogen";
	
	public static void main(String[] args)
	{
		
		try
		{
			List<StockData> stockDataList = InstrumentLoader.retrieveStockData("AAPL", "NBNUYC1WI13YC019");
			
			if(stockDataList != null)
			{
				List<SampleVector> sampleVectorList = new ArrayList<SampleVector>();
				for(StockData stockData : stockDataList)
				{
					sampleVectorList.addAll(InstrumentLoader.getSampleVectors(stockData));
				}
				
				TimeSeriesRepositoryDAO dao = 
						new TimeSeriesRepositoryDAOInfluxImpl(
								"http://localhost:8086", 
								"test", 
								"test", 
								"fintrader_instrument", 
								RETENTION_POLICY_AUTOGEN);
				
				dao.saveSampleVectors(new AggregateTimeSeries("AAPL", SampleFrequency.DAY), sampleVectorList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		
		
	}
	
	private static List<SampleVector> getSampleVectors(StockData stockData)
	{
		List<SampleVector> sampleVectorList = new ArrayList<SampleVector>();
		if(stockData != null)
		{
			SampleData[] priceSampleDatas = 
					new SampleData[] {
							new SampleData("open", BigDecimal.valueOf(stockData.getOpen())), 
							new SampleData("high", BigDecimal.valueOf(stockData.getHigh())),
							new SampleData("low", BigDecimal.valueOf(stockData.getLow())),
							new SampleData("close", BigDecimal.valueOf(stockData.getClose()))};
			SampleVector priceSampleVector = 
					new SampleVector(stockData.getDateTime().toEpochSecond(ZoneOffset.UTC), "currency/dollar", priceSampleDatas);
			
			SampleData[] volumeSampleDatas = 
					new SampleData[] {new SampleData("volume", BigDecimal.valueOf(stockData.getVolume()))};
			SampleVector volumeSampleVector = new SampleVector(stockData.getDateTime().toEpochSecond(ZoneOffset.UTC), "numberOfShares", volumeSampleDatas);
			
			sampleVectorList.add(priceSampleVector);
			sampleVectorList.add(volumeSampleVector);
		}
		return sampleVectorList;
	}
	
	private static List<StockData> retrieveStockData(String symbol, String apiKey)
	{
		List<StockData> stockDataList = null;
		
		int timeout = 3000;
		AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
		TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

		try
		{
			Daily response = stockTimeSeries.daily(symbol, OutputSize.FULL);
			Map<String, String> metaData = response.getMetaData();
			System.out.println("Information: " + metaData.get("1. Information"));
			System.out.println("Stock: " + metaData.get("2. Symbol"));

			stockDataList = response.getStockData();
//			stockData.forEach(stock -> {
//				System.out.println("date:   " + stock.getDateTime());
//				System.out.println("open:   " + stock.getOpen());
//				System.out.println("high:   " + stock.getHigh());
//				System.out.println("low:    " + stock.getLow());
//				System.out.println("close:  " + stock.getClose());
//				System.out.println("volume: " + stock.getVolume());
//			});
		}
		catch(AlphaVantageException e)
		{
			System.out.println("something went wrong");
		}		
		
		
		return stockDataList;
	}
	
	
//	private static String getInfluxDBLineFormat()
	
}