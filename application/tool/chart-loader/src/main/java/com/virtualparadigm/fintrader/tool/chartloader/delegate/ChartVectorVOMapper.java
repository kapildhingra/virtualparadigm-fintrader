package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.patriques.output.timeseries.data.StockData;

public class ChartVectorVOMapper
{
	public static ChartVectorVO map(StockData stockData)
	{
		ChartVectorVO instrumentData = null;
		if(stockData != null)
		{
			instrumentData = 
					new ChartVectorVO(
							stockData.getDateTime(), 
							BigDecimal.valueOf(stockData.getOpen()), 
							BigDecimal.valueOf(stockData.getHigh()), 
							BigDecimal.valueOf(stockData.getLow()), 
							BigDecimal.valueOf(stockData.getClose()), 
							BigDecimal.valueOf(stockData.getVolume()));
		}
		return instrumentData;
	}

	public static List<ChartVectorVO> map(List<StockData> stockDataList)
	{
		List<ChartVectorVO> instrumentDataList = null;
		if(stockDataList != null)
		{
			instrumentDataList = new ArrayList<ChartVectorVO>();
			for(StockData stockData : stockDataList)
			{
				instrumentDataList.add(ChartVectorVOMapper.map(stockData));
			}
		}
		return instrumentDataList;
	}
	
}