package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.Collection;

import org.joda.time.DateTime;

import com.virtualparadigm.fintrader.app.chart.service.api.InstrumentDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.InstrumentSeriesDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.InstrumentService;
import com.virtualparadigm.fintrader.app.chart.service.api.MeasurementDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTO;
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
	public Collection<InstrumentDTO> findInstruments()
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
	public InstrumentDTO createInstrument(String symbol, String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrumentDTO removeInstrument(GUID guid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMeasurement(GUID instrumentGUID, DateTime dateTime, BigDecimal measurement)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeasurements(GUID instrumentGUID, Collection<MeasurementDTO> measurements)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSample(GUID instrumentGUID, DateTime dateTime, BigDecimal sample)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSamples(GUID instrumentGUID, DateTime dateTime, Collection<SampleDTO> samples)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}