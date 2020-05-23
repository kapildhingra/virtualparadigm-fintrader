package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;

import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class ChartRepositoryDAOInfluxImpl implements ChartRepositoryDAO
{
	private static final VParadigmLogger logger = new VParadigmLogger(ChartRepositoryDAOInfluxImpl.class);
	private static final String TAG_UNIT_OF_MEASURE = "unitOfMeasure";
//	private static final String RETENTION_POLICY_AUTOGEN = "autogen";
	
	
	private String host;
	private String username;
	private String password;
	private String databaseName;
	private String retentionPolicy;
	private InfluxDB influxDB;
	
	public ChartRepositoryDAOInfluxImpl(String host, String username, String password, String databaseName, String retentionPolicy)
	{
		this.host = host;
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
		this.retentionPolicy = retentionPolicy;
		this.influxDB = InfluxDBFactory.connect(host, username, password);
		this.influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
		Pong pong = this.influxDB.ping();
		logger.debug("successfully connected to InfluxDB [host: " + host + ", version: " + pong.getVersion() + ", user: " + username + "]");
	}

	@Override
	public ChartData createChart(String userSpace, SampleFrequencyData sampleFrequency, String symbol, String chartName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartData getChart(ChartId chartId)
	{
		ChartData chartData = null;
		try
		{
			chartData = ChartDataFactory.createChartData(chartId);
			
		}
		catch(ParseException pe)
		{
			throw new RuntimeException("could not parse chartId", pe);
		}
		return chartData;
	}

	@Override
	public ChartData getChart(String userSpace, SampleFrequencyData sampleFrequency, String symbol, String chartName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartData> findCharts(String userSpace)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartData> findCharts(String userSpace, SampleFrequencyData sampleFrequency)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ChartData> findCharts(String userSpace, SampleFrequencyData sampleFrequency, String symbol)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSampleVector(ChartId chartId, SampleVectorData sampleVector)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSampleVectors(ChartId chartId, List<SampleVectorData> sampleVectors)
	{
		{
			VParadigmValidator.validateNotNull("chartId", chartId);
			VParadigmValidator.validateNotEmpty("sampleVector", sampleVectors);

			BatchPoints batchPoints = BatchPoints.database(this.databaseName).retentionPolicy(this.retentionPolicy).build();

			List<Point> pointList = null;
			for(SampleVectorData sampleVectorData : sampleVectors)
			{
				logger.trace("adding sample vectors to batch");
				pointList = ChartRepositoryDAOInfluxImpl.toPoints(chartId, sampleVectorData);
				if(pointList != null)
				{
					for(Point point : pointList)
					{
						batchPoints.point(point);
					}
				}
			}

			// InfluxDB influxDB = InfluxDBFactory.connect(host, username,
			// password);
			// influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
			logger.trace("writing batch");
			influxDB.write(batchPoints);
			influxDB.flush();
			// influxDB.close();
		}		
		
		
		
		// TODO Auto-generated method stub
		
	}
	
	
//	private static Point toPoint(ChartId chartId, SampleVectorData sampleVectorData)
//	{
//		VParadigmValidator.validateNotNull("chartId", chartId);
//		VParadigmValidator.validateNotNull("sampleVector", sampleVectorData);
//
//		Builder pointBuilder = Point.measurement(chartId.asString()).time(sampleVectorData.getGmtTimestamp(),
//				TimeUnit.MILLISECONDS);
//
//		SampleData[] sampleDatas = sampleVectorData.getSampleData();
//		if (sampleDatas != null)
//		{
//			for(SampleData sampleData : sampleDatas)
//			{
//				pointBuilder.addField(sampleData.getField(), sampleData.getValue());
//			}
//			pointBuilder.tag(TAG_UNIT_OF_MEASURE, sampleVectorData.getUnitOfMeasure());
//		}
//		Point p = pointBuilder.build();
//		logger.trace("adding measurement point for " + p.lineProtocol());
//		return p;
//		// return pointBuilder.build();
//	}

	private static List<Point> toPoints(ChartId chartId, SampleVectorData sampleVectorData)
	{
		//every sample with same unit of measure can be captured within the same point
		VParadigmValidator.validateNotNull("chartId", chartId);
		VParadigmValidator.validateNotNull("sampleVector", sampleVectorData);

		List<Point> pointList = new ArrayList<Point>();
		Builder pointBuilder = Point.measurement(chartId.asString()).time(sampleVectorData.getGmtTimestamp(),
				TimeUnit.MILLISECONDS);

		Map<String, List<SampleData>> unitOfMeasureSampleDataMap = ChartRepositoryDAOInfluxImpl.groupSampleDataByUnitOfMeasure(sampleVectorData);
		if(unitOfMeasureSampleDataMap != null)
		{
			List<SampleData> sampleDataList = null;
			Point point = null;
			for(String unitOfMeasure : unitOfMeasureSampleDataMap.keySet())
			{
				sampleDataList = unitOfMeasureSampleDataMap.get(unitOfMeasure);
				if(sampleDataList != null)
				{
					for(SampleData sampleData : sampleDataList)
					{
						pointBuilder.addField(sampleData.getField(), sampleData.getValue());
					}
				}
				pointBuilder.tag(TAG_UNIT_OF_MEASURE, unitOfMeasure);
				point = pointBuilder.build();
				logger.trace("adding measurement point for " + point.lineProtocol());
				pointList.add(pointBuilder.build());
			}
		}
		
		return pointList;
	}
	
	private static Map<String, List<SampleData>> groupSampleDataByUnitOfMeasure(SampleVectorData sampleVectorData)
	{
		Map<String, List<SampleData>> unitOfMeasureSampleDataMap = null;
		if(sampleVectorData != null)
		{
			SampleData[] sampleDatas = sampleVectorData.getSampleData();
			if (sampleDatas != null)
			{
				unitOfMeasureSampleDataMap = new HashMap<String, List<SampleData>>();
				List<SampleData> foundSampleDataList = null;
				for(SampleData sampleData : sampleDatas)
				{
					foundSampleDataList = unitOfMeasureSampleDataMap.get(sampleData.getUnitOfMeasure());
					if(foundSampleDataList == null)
					{
						foundSampleDataList = new ArrayList<SampleData>();
					}
					foundSampleDataList.add(sampleData);
				}
			}
		}
		return unitOfMeasureSampleDataMap;
	}
	

	@Override
	public void clearSampleVector(ChartId chartId, long sampleTimestamp)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSample(ChartId chartId, long sampleTimestamp, String field)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SampleVectorData> getSampleVectors(ChartId chartId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SampleVectorData getSampleVector(ChartId chartId, long sampleTimestamp)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSample(ChartId chartId, long sampleTimestamp, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDecimal> getSamples(ChartId chartId, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleVectorData> getSampleVectors(ChartId chartId, long startTimestamp, long endTimestamp)
	{
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
	
	
	
	
	
	
//	@Override
//	public ChartData saveAggregateTimeSeries(String name, SampleFrequency sampleFrequency)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ChartData getAggregateTimeSeries(GUID aggregateTimeSeriesGUID)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Collection<ChartData> findAggregateTimeSeries()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean saveSampleVector(ChartData aggregateTimeSeries, SampleVector sampleVector)
//	{
//		VParadigmValidator.validateNotNull("aggregateTimeSeries", aggregateTimeSeries);
//		VParadigmValidator.validateNotNull("sampleVector", sampleVector);
//		
//		InfluxDB influxDB = InfluxDBFactory.connect(host, username, password);
//		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
//		influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
//		influxDB.setRetentionPolicy(this.retentionPolicy);
//		influxDB.setDatabase(this.databaseName);
//		
//		influxDB.write(
//				ChartRepositoryDAOInfluxImpl.toPoint(
//							aggregateTimeSeries.getName(), 
//							aggregateTimeSeries.getSampleFrequency(), 
//							sampleVector));
//		influxDB.close();
//		return true;
//	}
//
//	@Override
//	public boolean saveSampleVectors(ChartData aggregateTimeSeries, List<SampleVector> sampleVectors)
//	{
//		VParadigmValidator.validateNotNull("aggregateTimeSeries", aggregateTimeSeries);
//		VParadigmValidator.validateNotEmpty("sampleVector", sampleVectors);
//		
//		BatchPoints batchPoints = 
//				BatchPoints.database(this.databaseName).retentionPolicy(this.retentionPolicy).build();
//		
//		for(SampleVector sampleVector : sampleVectors)
//		{
//System.out.println(">>>>>> adding sample vectors to batch");
//			batchPoints.point(
//					ChartRepositoryDAOInfluxImpl.toPoint(
//							aggregateTimeSeries.getName(), 
//							aggregateTimeSeries.getSampleFrequency(), 
//							sampleVector));
//		}
//		
////		InfluxDB influxDB = InfluxDBFactory.connect(host, username, password);
////		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
//System.out.println("writing batch <<<<<<<<<<<<<<<");
//		influxDB.write(batchPoints);
//		influxDB.flush();
////		influxDB.close();
//		return true;
//	}
//	
//	private static Point toPoint(String aggregateTimeSeriesName, SampleFrequency sampleFrequency, SampleVector sampleVector)
//	{
//		VParadigmValidator.validateNotEmpty("aggregateTimeSeriesName", aggregateTimeSeriesName);
//		VParadigmValidator.validateNotNull("sampleFrequency", sampleFrequency);
//		VParadigmValidator.validateNotNull("sampleVector", sampleVector);
//		
//		Builder pointBuilder = Point.measurement(aggregateTimeSeriesName+"-"+sampleFrequency.toString()).time(sampleVector.getGmtTimestamp(), TimeUnit.MILLISECONDS);
//		//logger.trace("adding measurement point for " + aggregateTimeSeriesName+"-"+sampleFrequency.toString() + " at time: " + sampleVector.getGmtTimestamp());
//		
//		SampleData[] sampleDatas = sampleVector.getSampleData();
//		if(sampleDatas != null)
//		{
//			for(SampleData sampleData : sampleDatas)
//			{
//				pointBuilder.addField(sampleData.getField(), sampleData.getValue());
//			}
//			pointBuilder.tag(TAG_UNIT_OF_MEASURE, sampleVector.getUnitOfMeasure());
//		}
//		Point p = pointBuilder.build();
//		logger.trace("adding measurement point for " + p.lineProtocol());
//		return p;
////		return pointBuilder.build();
//	}
//
//	
//	
//	@Override
//	public boolean clearSampleVector(long sampleTimestamp)
//	{
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean clearSample(long sampleTimestamp, String field)
//	{
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public List<SampleVector> getSampleVectors(GUID aggregateTimeSeriesGUID)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public SampleVector getSampleVector(GUID aggregateTimeSeriesGUID, long sampleTimestamp)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public BigDecimal getSample(GUID aggregateTimeSeriesGUID, long sampleTimestamp, String field)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<BigDecimal> getSamples(GUID aggregateTimeSeriesGUID, String field)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
// ==============================================================================================	
	
//	public static void main(String[] args)
//	{
//		try
//		{
//			TimeSeriesRepositoryDAO dao = 
//					new TimeSeriesRepositoryDAOInfluxImpl(
//							"http://localhost:8086", 
//							"test", 
//							"test", 
//							"fintrader_instrument", 
//							RETENTION_POLICY_AUTOGEN);
//			
//			SampleData[] sampleDatas = 
//					new SampleData[] {
//							new SampleData("open", BigDecimal.valueOf(400)), 
//							new SampleData("high", BigDecimal.valueOf(410)),
//							new SampleData("low", BigDecimal.valueOf(390)),
//							new SampleData("close", BigDecimal.valueOf(405))};
//			
//			SampleData[] sampleDatas2 = 
//					new SampleData[] {new SampleData("volume", BigDecimal.valueOf(4000000))};
//			
//			//SampleData[] sampleData = new SampleData[]{new SampleData("open", BigDecimal.valueOf(100))};
//			SampleVector sampleVector = new SampleVector(System.currentTimeMillis(), "currency/dollar", sampleDatas);
////			dao.saveSampleVector(new AggregateTimeSeries("AAPL", SampleFrequency.DAY), sampleVector);
//			
//			SampleVector sampleVector2 = new SampleVector(System.currentTimeMillis(), "numberOfShares", sampleDatas2);
////			dao.saveSampleVector(new AggregateTimeSeries("AAPL", SampleFrequency.DAY), sampleVector2);
//			
//			List<SampleVector> sampleVectorList = new ArrayList<SampleVector>();
//			sampleVectorList.add(sampleVector);
//			sampleVectorList.add(sampleVector2);
//			dao.saveSampleVectors(new TimeSeriesMatrix("AAPL", SampleFrequency.DAY), sampleVectorList);
//			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
	
//	@Override
//	public boolean saveSampleVectors(AggregateTimeSeries aggregateTimeSeries, List<SampleVector> sampleVectors)
//	{
//		VParadigmValidator.validateNotNull("aggregateTimeSeries", aggregateTimeSeries);
//		VParadigmValidator.validateNotEmpty("sampleVector", sampleVectors);
//		
//		BatchPoints batchPoints = 
//				BatchPoints.database(this.databaseName).retentionPolicy(this.retentionPolicy).build();
//		
//		for(SampleVector sampleVector : sampleVectors)
//		{
//System.out.println(">>>>>> adding sample vectors to batch");
//			batchPoints.point(
//					TimeSeriesRepositoryDAOInfluxImpl.toPoint(
//							aggregateTimeSeries.getName(), 
//							aggregateTimeSeries.getSampleFrequency(), 
//							sampleVector));
//		}
//		
//		InfluxDB influxDB = InfluxDBFactory.connect(host, username, password);
//		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
//System.out.println("writing batch <<<<<<<<<<<<<<<");
//		influxDB.write(batchPoints);
//		influxDB.close();
//		return true;
//	}	
	
	
}