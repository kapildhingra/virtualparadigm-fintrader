package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.util.ArrayList;
import java.util.List;

import org.patriques.output.timeseries.data.StockData;

public class ChartDataMapper
{
	public static ChartData map(StockData stockData)
	{
		ChartData instrumentData = null;
		if(stockData != null)
		{
			instrumentData = 
					new ChartData(
							stockData.getDateTime(), 
							stockData.getOpen(), 
							stockData.getHigh(), 
							stockData.getLow(), 
							stockData.getClose(), 
							stockData.getVolume(), 
							stockData.getDividendAmount(), 
							stockData.getSplitCoefficient());
		}
		return instrumentData;
	}

	public static List<ChartData> map(List<StockData> stockDataList)
	{
		List<ChartData> instrumentDataList = null;
		if(stockDataList != null)
		{
			instrumentDataList = new ArrayList<ChartData>();
			for(StockData stockData : stockDataList)
			{
				instrumentDataList.add(ChartDataMapper.map(stockData));
			}
		}
		return instrumentDataList;
	}
	
}