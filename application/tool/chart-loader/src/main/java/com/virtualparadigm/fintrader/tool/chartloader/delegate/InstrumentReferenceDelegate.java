package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.util.List;

public interface InstrumentReferenceDelegate
{
	List<ExchangeVO> getExchanges();
	List<String> getSectors();
	List<String> getIndexes();
	List<InstrumentVO> getInstrumentsByExchange(String exchange);
	List<InstrumentVO> getInstrumentsBySector(String sector);
	List<InstrumentVO> getInstrumentsByIndex(String index);
}