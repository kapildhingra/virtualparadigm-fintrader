package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.util.Collection;
import java.util.List;

import com.virtualparadigm.fintrader.app.chart.service.api.InstrumentDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.InstrumentSeriesDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.InstrumentService;
import com.virtualparadigm.fintrader.app.chart.service.api.MeasurementDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleSeriesDTO;
import com.vparadigm.shared.comp.common.identity.GUID;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

public class InstrumentServiceImpl implements InstrumentService
{
	private static final VParadigmLogger logger = new VParadigmLogger(InstrumentServiceImpl.class);

	@Override
	public InstrumentDTO getInstrument(GUID guid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrumentDTO getInstrumentBySymbol(String symbol)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<InstrumentDTO> findInstrument()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrumentSeriesDTO getInstrumentSeries(GUID instrumentGUID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrumentDTO saveInstrument(String symbol, String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeInstrument(GUID guid)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeasurement(GUID instrumentGUID, MeasurementDTO measurementDTO)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeasurements(GUID instrumentGUID, List<MeasurementDTO> measurementDTOs)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSample(GUID instrumentGUID, SampleDTO sampleDTO)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSamples(GUID instrumentGUID, SampleSeriesDTO sampleSeriesDTO)
	{
		// TODO Auto-generated method stub
		
	}



	
}