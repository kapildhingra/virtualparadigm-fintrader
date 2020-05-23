package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.util.List;

public interface ChartReferenceDelegate
{
	List<String> getExchanges();
	List<String> getSectors();
	List<String> getIndexes();
	List<Instrument> getInstrumentsByExchange(String exchange);
	List<Instrument> getInstrumentsBySector(String sector);
	List<Instrument> getInstrumentsByIndex(String index);
}