package com.virtualparadigm.fintrader.app.chart.service.api;

import java.util.Collection;
import java.util.List;

import com.vparadigm.shared.comp.common.identity.GUID;

public interface InstrumentService
{
	InstrumentDTO getInstrument(GUID guid);
	InstrumentDTO getInstrumentBySymbol(String symbol);
	Collection<InstrumentDTO> findInstrument();
	
	InstrumentSeriesDTO getInstrumentSeries(GUID instrumentGUID);
	
	InstrumentDTO saveInstrument(String symbol, String name);
	void removeInstrument(GUID guid);
	
	void addMeasurement(GUID instrumentGUID, MeasurementDTO measurementDTO);
	void addMeasurements(GUID instrumentGUID, List<MeasurementDTO> measurementDTOs);
	
	void setSample(GUID instrumentGUID, SampleDTO sampleDTO);
	void setSamples(GUID instrumentGUID, SampleSeriesDTO sampleSeriesDTO);
}