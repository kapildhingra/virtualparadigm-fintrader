package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.util.ArrayList;
import java.util.List;

import com.virtualparadigm.fintrader.tool.chartloader.delegate.MarketVO;

public class MarketCLOMapper
{
	public static MarketCLO toMarketCLO(MarketVO marketVO)
	{
		MarketCLO marketCLO = null;
		if(marketVO != null)
		{
			marketCLO = new MarketCLO(marketVO.getMarketIdentifierCode(), marketVO.getDateTimeZone());
		}
		return marketCLO;
	}
	
	public static List<MarketCLO> toMarketCLOs(List<MarketVO> marketVOList)
	{
		List<MarketCLO> marketDataList = null;
		if(marketVOList != null)
		{
			marketDataList = new ArrayList<MarketCLO>();
			for(MarketVO marketVO : marketVOList)
			{
				marketDataList.add(MarketCLOMapper.toMarketCLO(marketVO));
			}
		}
		return marketDataList;
	}
	
}