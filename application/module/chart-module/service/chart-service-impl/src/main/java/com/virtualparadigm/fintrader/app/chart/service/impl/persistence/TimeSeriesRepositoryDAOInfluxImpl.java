package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import com.virtualparadigm.fintrader.app.chart.service.impl.bean.InstrumentServiceImpl;
import com.vparadigm.shared.comp.common.identity.GUID;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class TimeSeriesRepositoryDAOInfluxImpl implements TimeSeriesRepositoryDAO
{
	private static final VParadigmLogger logger = new VParadigmLogger(InstrumentServiceImpl.class);
	private static final String TAG_UNIT_OF_MEASURE = "unitOfMeasure";
	private static final String RETENTION_POLICY_AUTOGEN = "autogen";
	
	
	private String host;
	private String username;
	private String password;
	private String databaseName;
	private String retentionPolicy;
//	private InfluxDB influxDB;
	
	public TimeSeriesRepositoryDAOInfluxImpl(String host, String username, String password, String databaseName, String retentionPolicy)
	{
		this.host = host;
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
		this.retentionPolicy = retentionPolicy;
//		this.influxDB = InfluxDBFactory.connect(host, username, password);
//		this.influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
//		Pong pong = this.influxDB.ping();
//		logger.debug("successfully connected to InfluxDB [host: " + host + ", version: " + pong.getVersion() + ", user: " + username + "]");
	}
	
	public static void main(String[] args)
	{
		try
		{
			TimeSeriesRepositoryDAO dao = 
					new TimeSeriesRepositoryDAOInfluxImpl(
							"http://localhost:8086", 
							"test", 
							"test", 
							"fintrader_instrument", 
							RETENTION_POLICY_AUTOGEN);
			
			SampleData[] sampleDatas = 
					new SampleData[] {
							new SampleData("open", BigDecimal.valueOf(400)), 
							new SampleData("high", BigDecimal.valueOf(410)),
							new SampleData("low", BigDecimal.valueOf(390)),
							new SampleData("close", BigDecimal.valueOf(405))};
			
			SampleData[] sampleDatas2 = 
					new SampleData[] {new SampleData("volume", BigDecimal.valueOf(4000000))};
			
			//SampleData[] sampleData = new SampleData[]{new SampleData("open", BigDecimal.valueOf(100))};
			SampleVector sampleVector = new SampleVector(System.currentTimeMillis(), "currency/dollar", sampleDatas);
//			dao.saveSampleVector(new AggregateTimeSeries("AAPL", SampleFrequency.DAY), sampleVector);
			
			SampleVector sampleVector2 = new SampleVector(System.currentTimeMillis(), "numberOfShares", sampleDatas2);
//			dao.saveSampleVector(new AggregateTimeSeries("AAPL", SampleFrequency.DAY), sampleVector2);
			
			List<SampleVector> sampleVectorList = new ArrayList<SampleVector>();
			sampleVectorList.add(sampleVector);
			sampleVectorList.add(sampleVector2);
			dao.saveSampleVectors(new AggregateTimeSeries("AAPL", SampleFrequency.DAY), sampleVectorList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	public AggregateTimeSeries saveAggregateTimeSeries(String name, SampleFrequency sampleFrequency)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AggregateTimeSeries getAggregateTimeSeries(GUID aggregateTimeSeriesGUID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<AggregateTimeSeries> findAggregateTimeSeries()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveSampleVector(AggregateTimeSeries aggregateTimeSeries, SampleVector sampleVector)
	{
		VParadigmValidator.validateNotNull("aggregateTimeSeries", aggregateTimeSeries);
		VParadigmValidator.validateNotNull("sampleVector", sampleVector);
		
		InfluxDB influxDB = InfluxDBFactory.connect(host, username, password);
		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
		influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
		influxDB.setRetentionPolicy(this.retentionPolicy);
		influxDB.setDatabase(this.databaseName);
		
		influxDB.write(
				TimeSeriesRepositoryDAOInfluxImpl.getPoint(
							aggregateTimeSeries.getName(), 
							aggregateTimeSeries.getSampleFrequency(), 
							sampleVector));
		influxDB.close();
		return true;
	}

	@Override
	public boolean saveSampleVectors(AggregateTimeSeries aggregateTimeSeries, List<SampleVector> sampleVectors)
	{
		VParadigmValidator.validateNotNull("aggregateTimeSeries", aggregateTimeSeries);
		VParadigmValidator.validateNotEmpty("sampleVector", sampleVectors);
		
		BatchPoints batchPoints = 
				BatchPoints.database(this.databaseName).retentionPolicy(this.retentionPolicy).build();
		
		for(SampleVector sampleVector : sampleVectors)
		{
System.out.println(">>>>>> adding sample vectors to batch");
			batchPoints.point(
					TimeSeriesRepositoryDAOInfluxImpl.getPoint(
							aggregateTimeSeries.getName(), 
							aggregateTimeSeries.getSampleFrequency(), 
							sampleVector));
		}
		
		InfluxDB influxDB = InfluxDBFactory.connect(host, username, password);
		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
System.out.println("writing batch <<<<<<<<<<<<<<<");
		influxDB.write(batchPoints);
		influxDB.close();
		return true;
	}
	
	private static Point getPoint(String aggregateTimeSeriesName, SampleFrequency sampleFrequency, SampleVector sampleVector)
	{
		VParadigmValidator.validateNotEmpty("aggregateTimeSeriesName", aggregateTimeSeriesName);
		VParadigmValidator.validateNotNull("sampleFrequency", sampleFrequency);
		VParadigmValidator.validateNotNull("sampleVector", sampleVector);
		
		Builder pointBuilder = Point.measurement(aggregateTimeSeriesName+"-"+sampleFrequency.toString()).time(sampleVector.getGmtTimestamp(), TimeUnit.MILLISECONDS);
System.out.println("  adding measurement point for " + aggregateTimeSeriesName+"-"+sampleFrequency.toString() + " at time: " + sampleVector.getGmtTimestamp());
		
		SampleData[] sampleDatas = sampleVector.getSampleData();
		if(sampleDatas != null)
		{
			for(SampleData sampleData : sampleDatas)
			{
				pointBuilder.addField(sampleData.getField(), sampleData.getValue());
			}
			pointBuilder.tag(TAG_UNIT_OF_MEASURE, sampleVector.getUnitOfMeasure());
		}
		return pointBuilder.build();
	}

	
	
	@Override
	public boolean clearSampleVector(long sampleTimestamp)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clearSample(long sampleTimestamp, String field)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SampleVector> getSampleVectors(GUID aggregateTimeSeriesGUID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SampleVector getSampleVector(GUID aggregateTimeSeriesGUID, long sampleTimestamp)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSample(GUID aggregateTimeSeriesGUID, long sampleTimestamp, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDecimal> getSamples(GUID aggregateTimeSeriesGUID, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}