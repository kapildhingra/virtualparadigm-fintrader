package com.virtualparadigm.fintrader.app.chart.service.api;

import java.math.BigDecimal;
import java.util.Collection;

import org.joda.time.DateTime;

import com.vparadigm.shared.comp.common.identity.GUID;

public interface InstrumentService
{
	InstrumentDTO getInstrument(GUID guid);
	InstrumentDTO getInstrumentBySymbol(String symbol);
	Collection<InstrumentDTO> findInstruments();
	
	InstrumentSeriesDTO getInstrumentSeries(GUID instrumentGUID);
	
	InstrumentDTO createInstrument(String symbol, String name);
	InstrumentDTO removeInstrument(GUID guid);
	
	void addMeasurement(GUID instrumentGUID, DateTime dateTime, BigDecimal measurement);
	void addMeasurements(GUID instrumentGUID, Collection<MeasurementDTO> measurements);
	
	void setSample(GUID instrumentGUID, DateTime dateTime, BigDecimal sample);
	void setSamples(GUID instrumentGUID, DateTime dateTime, Collection<SampleDTO> samples);
}