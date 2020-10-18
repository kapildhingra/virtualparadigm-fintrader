package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.util.List;

public interface MarketReferenceDelegate
{
	List<MarketVO> getMarkets();
	List<MarketVO> getMarketsByCountry(String country);
}