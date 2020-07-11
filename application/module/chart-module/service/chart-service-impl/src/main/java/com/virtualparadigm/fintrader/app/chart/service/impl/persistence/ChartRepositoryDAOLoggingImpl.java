package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class ChartRepositoryDAOLoggingImpl implements ChartRepositoryDAO
{
	private static final VParadigmLogger logger = new VParadigmLogger(ChartRepositoryDAOLoggingImpl.class);
	
	public ChartRepositoryDAOLoggingImpl()
	{
		logger.info("ChartRepositoryDAOLoggingImpl::ChartRepositoryDAOLoggingImpl()");
	}

	@Override
	public ChartRecord createChart(String userSpace, SampleRecordFrequency sampleFrequency, String symbol, String chartName)
	{
		logger.info("ChartRepositoryDAOLoggingImpl::createChart()");
		logger.info("userSpace: " + userSpace + " sampleFrequency: " + sampleFrequency+ " symbol: " + symbol+ " chartName: " + chartName);
		return new ChartRecord(userSpace,sampleFrequency, symbol, chartName);
	}

	@Override
	public void createUserSpace(String userSpace)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<String> getUserSpaces()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserSpace(String userSpace)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUserSpace(String userSpace)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public ChartRecord getChart(String userSpace, ChartId chartId)
	{
		ChartRecord chartData = null;
		try
		{
			chartData = ChartRecordFactory.createChartRecord(userSpace, chartId);
			
		}
		catch(ParseException pe)
		{
			throw new RuntimeException("could not parse chartId", pe);
		}
		return chartData;
	}

	@Override
	public ChartRecord getChart(String userSpace, SampleRecordFrequency sampleFrequency, String symbol, String chartName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartRecord> findCharts(String userSpace)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartRecord> findCharts(String userSpace, SampleRecordFrequency sampleFrequency)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartRecord> findCharts(String userSpace, SampleRecordFrequency sampleFrequency, String symbol)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSampleVector(String userSpace, ChartId chartId, SampleVectorRecord sampleVector)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSampleVectors(String userSpace, ChartId chartId, List<SampleVectorRecord> sampleVectors)
	{
		VParadigmValidator.validateNotNull("chartId", chartId);
		VParadigmValidator.validateNotEmpty("sampleVector", sampleVectors);

		logger.info("ChartRepositoryDAOLoggingImpl::createChart()");
		logger.info("chartId: " + chartId);
		StringBuffer strBuf = null;
		SampleRecord[] sampleRecords = null;
		for(SampleVectorRecord sampleVectorRecord : sampleVectors)
		{
			strBuf = new StringBuffer();
			strBuf.append("gmtTimestamp: ");
			strBuf.append(sampleVectorRecord.getGmtTimestamp());
			sampleRecords = sampleVectorRecord.getSampleRecords();
			if(sampleRecords != null)
			{
				strBuf.append(" sampleRecords: [");
				for(SampleRecord sampleRecord : sampleRecords)
				{
					strBuf.append("field: ");
					strBuf.append(sampleRecord.getField());
					strBuf.append(", value: ");
					strBuf.append(sampleRecord.getValue());
					strBuf.append(", unitOfMeasure: ");
					strBuf.append(sampleRecord.getUnitOfMeasure());
				}
				strBuf.append("]");
			}
			logger.info(strBuf.toString());
		}
	}
	

	@Override
	public void clearSampleVector(String userSpace, ChartId chartId, long sampleTimestamp)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSample(String userSpace, ChartId chartId, long sampleTimestamp, String field)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SampleVectorRecord> getSampleVectors(String userSpace, ChartId chartId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SampleVectorRecord getSampleVector(String userSpace, ChartId chartId, long sampleTimestamp)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSample(String userSpace, ChartId chartId, long sampleTimestamp, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDecimal> getSamples(String userSpace, ChartId chartId, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleVectorRecord> getSampleVectors(String userSpace, ChartId chartId, long startTimestamp, long endTimestamp)
	{
		// TODO Auto-generated method stub
		return null;
	}


	
}