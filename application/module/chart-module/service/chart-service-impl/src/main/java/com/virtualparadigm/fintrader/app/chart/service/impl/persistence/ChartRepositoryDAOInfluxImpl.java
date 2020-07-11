package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

import okhttp3.OkHttpClient;

public class ChartRepositoryDAOInfluxImpl implements ChartRepositoryDAO
{
	private static final VParadigmLogger logger = new VParadigmLogger(ChartRepositoryDAOInfluxImpl.class);
	private static final String TAG_UNIT_OF_MEASURE = "unitOfMeasure";
	
	private static final String RETENTION_POLICY_AUTOGEN = "autogen";
	private static final String RETENTION_POLICY_DEFAULT_DURATION = "INF";
	private static final int RETENTION_POLICY_DEFAULT_REPLICATION = 1;
	
	private static final String QUERY_DATABASES = "SHOW DATABASES";
	private static final String QUERY_SHOW_RETENTION_POLICIES = "SHOW RETENTION POLICIES ON %s";
	private static final String QUERY_CREATE_RETENTION_POLICY = "CREATE RETENTION POLICY %s ON %s duration %s replication %d";
	private static final String QUERY_DROP_RETENTION_POLICY = "DROP RETENTION POLICY %S ON %s";
	
	private static final String QUERY_SELECT_MEASUREMENT = "SELECT * FROM %s.%s";
	private static final String WHERE_BETWEEN_INCLUSIVE = " WHERE time >= %d AND time <= %d";
	
	private static final String QUERY_SHOW_MEASUREMENTS = "SHOW MEASUREMENTS ON %s";
	
	
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String databaseName;
//	private String retentionPolicy;
//	private InfluxDB influxDB;
	
	public ChartRepositoryDAOInfluxImpl(String host, int port, String username, String password, String databaseName)
	{
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
		//this.retentionPolicy = retentionPolicy;
//		try
//		{
//			this.influxDB = InfluxDBFactory.connect(ChartRepositoryDAOInfluxImpl.getConnectionString(host, port), username, password);
//			this.influxDB.enableBatch(100, 500, TimeUnit.MILLISECONDS);
//			this.influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
//			
//			this.influxDB.setRetentionPolicy(this.retentionPolicy);
//			this.influxDB.setDatabase(this.databaseName);
//			
//			Pong pong = this.influxDB.ping();
//			logger.debug("successfully connected to InfluxDB [host: " + host + ", version: " + pong.getVersion() + ", user: " + username + "]");
//		}
//		catch(Exception e)
//		{
//			logger.warning("could not connect to chart repository");
//		}
	}
	
	@Override
	public void createUserSpace(String userSpace)
	{
		InfluxDB influxDB = null;
		try
		{
			influxDB = 
					ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
							this.host, 
							this.port, 
							this.username, 
							this.password, 
							this.databaseName);
			
			logger.trace("creating retention policy on: " + this.databaseName);
			QueryResult queryResult = 
					influxDB.query(
							new Query(
									ChartRepositoryDAOInfluxImpl.evaluateQuery(
											QUERY_CREATE_RETENTION_POLICY, 
											userSpace, 
											this.databaseName, 
											RETENTION_POLICY_DEFAULT_DURATION, 
											RETENTION_POLICY_DEFAULT_REPLICATION)));

			if(queryResult == null || queryResult.hasError())
			{
				String errorMessage = queryResult.getError() + " while creating retention policy: " + userSpace + " duration: " + RETENTION_POLICY_DEFAULT_DURATION + " replication: " + RETENTION_POLICY_DEFAULT_REPLICATION;
				logger.warning(errorMessage);
				throw new ChartRepositoryExecutionException(errorMessage);
			}
		}
		finally
		{
			if(influxDB != null)
			{
				influxDB.close();
			}
		}
	}

	@Override
	public Collection<String> getUserSpaces()
	{
		Set<String> userSpaceSet = null;
		InfluxDB influxDB = null;
		try
		{
			influxDB = 
					ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
							this.host, 
							this.port, 
							this.username, 
							this.password, 
							this.databaseName);
			
			userSpaceSet = ChartRepositoryDAOInfluxImpl.getRetentionPolicies(this.databaseName, influxDB);

		}
		finally
		{
			if(influxDB != null)
			{
				influxDB.close();
			}
		}
		return userSpaceSet;
	}

	@Override
	public String getUserSpace(String userSpace)
	{
		String foundUserSpace = null;
		if(StringUtils.isNotEmpty(userSpace))
		{
			InfluxDB influxDB = null;
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);
				
				foundUserSpace = ChartRepositoryDAOInfluxImpl.getRetentionPolicy(userSpace, this.databaseName, influxDB);
			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
		}
		return foundUserSpace;
	}

	@Override
	public void removeUserSpace(String userSpace)
	{
		InfluxDB influxDB = null;
		try
		{
			influxDB = 
					ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
							this.host, 
							this.port, 
							this.username, 
							this.password, 
							this.databaseName);
			
			logger.trace("dropping retention policy: " + userSpace + " on: " + this.databaseName);
			QueryResult queryResult = 
					influxDB.query(
							new Query(
									ChartRepositoryDAOInfluxImpl.evaluateQuery(
											QUERY_DROP_RETENTION_POLICY, 
											userSpace, 
											this.databaseName)));

			if(queryResult == null || queryResult.hasError())
			{
				String errorMessage = queryResult.getError() + " while dropping retention policy: " + userSpace + " on: " + this.databaseName;
				logger.warning(errorMessage);
				throw new ChartRepositoryExecutionException(errorMessage);
			}
		}
		finally
		{
			if(influxDB != null)
			{
				influxDB.close();
			}
		}
	}
	
	
	
	@Override
	public ChartRecord createChart(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName)
	{
		ChartRecord chartRecord = null;
		InfluxDB influxDB = null;
		try
		{
			influxDB = 
					ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
							this.host, 
							this.port, 
							this.username, 
							this.password, 
							this.databaseName);
			
			String retentionPolicy = ChartRepositoryDAOInfluxImpl.getRetentionPolicy(userSpace, this.databaseName, influxDB);
			if(retentionPolicy != null)
			{
				chartRecord = ChartRecordFactory.createChartRecord(retentionPolicy, sampleRecordFrequency, symbol, chartName);
			}
			else
			{
				logger.warning("could not find retention policy: " + userSpace + " duration: " + RETENTION_POLICY_DEFAULT_DURATION + " replication: " + RETENTION_POLICY_DEFAULT_REPLICATION);
			}
		}
		catch(Throwable t)
		{
			logger.error("could not write sample vectors", t);
		}
		finally
		{
			if(influxDB != null)
			{
				influxDB.close();
			}
		}
		return chartRecord;
	}

	@Override
	public ChartRecord getChart(String userSpace, ChartId chartId)
	{
		ChartRecord chartRecord = null;
		if(chartId != null)
		{
			chartRecord = this.getChart(userSpace, chartId.getSampleRecordFrequency(), chartId.getSymbol(), chartId.getChartName());
		}
		return chartRecord;
	}

	@Override
	public ChartRecord getChart(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName)
	{
		ChartRecord chartRecord = null;
		InfluxDB influxDB = null;
		if(StringUtils.isNotEmpty(userSpace) && sampleRecordFrequency != null && StringUtils.isNotEmpty(symbol) && StringUtils.isNotEmpty(chartName))
		{
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);
				
				//"charts" or "measurement" in influxdb is created on demand, so if the retention policy exists, consider the chart to exist
				String retentionPolicy = ChartRepositoryDAOInfluxImpl.getRetentionPolicy(userSpace, this.databaseName, influxDB);
				if(retentionPolicy != null)
				{
					chartRecord = ChartRecordFactory.createChartRecord(userSpace, sampleRecordFrequency, symbol, chartName);
				}
			}
			catch(Throwable t)
			{
				logger.error("could not write sample vectors", t);
			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
		}
		return chartRecord;
	}

	@Override
	public Collection<ChartRecord> findCharts(String userSpace)
	{
		final Set<ChartRecord> resultChartRecords = new HashSet<ChartRecord>();
		InfluxDB influxDB = null;
		if(StringUtils.isNotEmpty(userSpace))
		{
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);
				

				QueryResult queryResult = 
						influxDB.query(
								new Query(
										ChartRepositoryDAOInfluxImpl.evaluateQuery(
												QUERY_SHOW_MEASUREMENTS, 
												this.databaseName)));
				
				if(queryResult == null || queryResult.hasError())
				{
					String errorMessage = queryResult.getError() + " while fetching measurements for: " + userSpace + " on: " + this.databaseName;
					logger.warning(errorMessage);
					throw new ChartRepositoryExecutionException(errorMessage);
				}				
				else
				{
					ChartRepositoryDAOInfluxImpl.processChartRecordsFromResult(
							queryResult, 
							userSpace,
							(foundChartRecord) -> {
								if(userSpace.equals(foundChartRecord.getUserspace()))
								{
									resultChartRecords.add(foundChartRecord);
								}
							});
				}
			}
			catch(Throwable t)
			{
				logger.error("could find charts for userSpace: " + userSpace, t);
			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
		}
		return resultChartRecords;
	}

	@Override
	public Collection<ChartRecord> findCharts(String userSpace, SampleRecordFrequency sampleRecordFrequency)
	{
		final Set<ChartRecord> resultChartRecords = new HashSet<ChartRecord>();
		InfluxDB influxDB = null;
		if(StringUtils.isNotEmpty(userSpace) && sampleRecordFrequency != null)
		{
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);

				QueryResult queryResult = 
						influxDB.query(
								new Query(
										ChartRepositoryDAOInfluxImpl.evaluateQuery(
												QUERY_SHOW_MEASUREMENTS, 
												this.databaseName)));

				if(queryResult == null || queryResult.hasError())
				{
					String errorMessage = queryResult.getError() + " while fetching measurements for: " + userSpace + " with sample frequency: " + sampleRecordFrequency + " on: " + this.databaseName;
					logger.warning(errorMessage);
					throw new ChartRepositoryExecutionException(errorMessage);
				}				
				else
				{
					ChartRepositoryDAOInfluxImpl.processChartRecordsFromResult(
							queryResult, 
							userSpace, 
							(foundChartRecord) -> {
								if(userSpace.equals(foundChartRecord.getUserspace()) && foundChartRecord.getSampleRecordFrequency() == sampleRecordFrequency)
								{
									resultChartRecords.add(foundChartRecord);
								}
							});
				}
			}
			catch(Throwable t)
			{
				logger.error("could find charts for userSpace: " + userSpace + " sampleRecordFrequency: " + sampleRecordFrequency, t);
			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
		}
		return resultChartRecords;
	}

	@Override
	public Collection<ChartRecord> findCharts(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol)
	{
		final Set<ChartRecord> resultChartRecords = new HashSet<ChartRecord>();
		InfluxDB influxDB = null;
		if(StringUtils.isNotEmpty(userSpace) && sampleRecordFrequency != null && StringUtils.isNotEmpty(symbol))
		{
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);
				
				
				QueryResult queryResult = 
						influxDB.query(
								new Query(
										ChartRepositoryDAOInfluxImpl.evaluateQuery(
												QUERY_SHOW_MEASUREMENTS, 
												this.databaseName)));

				if(queryResult == null || queryResult.hasError())
				{
					String errorMessage = queryResult.getError() + " while fetching measurements for: " + userSpace + " with sample frequency: " + sampleRecordFrequency + " and symbol: " + symbol + " on: " + this.databaseName;
					logger.warning(errorMessage);
					throw new ChartRepositoryExecutionException(errorMessage);
				}				
				else
				{
					ChartRepositoryDAOInfluxImpl.processChartRecordsFromResult(
							queryResult, 
							userSpace, 
							(foundChartRecord) -> {
								if(userSpace.equals(foundChartRecord.getUserspace()) && 
									foundChartRecord.getSampleRecordFrequency() == sampleRecordFrequency && 
									symbol.equals(foundChartRecord.getSymbol()))
								{
									resultChartRecords.add(foundChartRecord);
								}
							});
				}
			}
			catch(Throwable t)
			{
				logger.error("could find charts for userSpace: " + userSpace + " sampleRecordFrequency: " + sampleRecordFrequency + " symbol: " + symbol, t);
			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
		}
		return resultChartRecords;
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

		if(chartId != null)
		{
			BatchPoints batchPoints = BatchPoints.database(this.databaseName).retentionPolicy(userSpace).build();

			List<Point> pointList = null;
			for(SampleVectorRecord sampleVectorRecord : sampleVectors)
			{
				logger.trace("adding sample vectors to batch");
				pointList = ChartRepositoryDAOInfluxImpl.toPoints(chartId, sampleVectorRecord);
				if(pointList != null)
				{
					for(Point point : pointList)
					{
						batchPoints.point(point);
					}
				}
			}
			
			InfluxDB influxDB = null;
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);
				
				influxDB.write(batchPoints);
				logger.trace("wrote batch points");
				influxDB.flush();
				logger.trace("flushed batch points");
			}
			catch(Throwable t)
			{
				logger.error("could not write sample vectors", t);
			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
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
		
	}

	@Override
	public List<SampleVectorRecord> getSampleVectors(String userSpace, ChartId chartId)
	{
		return null;
	}

	@Override
	public SampleVectorRecord getSampleVector(String userSpace, ChartId chartId, long sampleTimestamp)
	{
		return null;
	}

	@Override
	public BigDecimal getSample(String userSpace, ChartId chartId, long sampleTimestamp, String field)
	{
		return null;
	}

	@Override
	public List<BigDecimal> getSamples(String userSpace, ChartId chartId, String field)
	{
		return null;
	}

	@Override
	public List<SampleVectorRecord> getSampleVectors(String userSpace, ChartId chartId, long startTimestamp, long endTimestamp)
	{
		List<SampleVectorRecord> sampleVectorRecordList = null;
		if(userSpace != null && chartId != null)
		{
			InfluxDB influxDB = null;
			try
			{
				influxDB = 
						ChartRepositoryDAOInfluxImpl.getInfluxDBConnection(
								this.host, 
								this.port, 
								this.username, 
								this.password, 
								this.databaseName);
				
				logger.trace("getting sample vectors for userSpace: " + userSpace + " chartId: " + chartId.asString() + " on: " + this.databaseName);
				String q = ChartRepositoryDAOInfluxImpl.evaluateQuery(
						QUERY_SELECT_MEASUREMENT + WHERE_BETWEEN_INCLUSIVE, 
						userSpace, 
						this.databaseName, 
						startTimestamp, 
						endTimestamp);
				QueryResult queryResult = 
						influxDB.query(
								new Query(
										ChartRepositoryDAOInfluxImpl.evaluateQuery(
												QUERY_SELECT_MEASUREMENT + WHERE_BETWEEN_INCLUSIVE, 
												userSpace, 
												this.databaseName, 
												startTimestamp, 
												endTimestamp)));

				if(queryResult == null || queryResult.hasError())
				{
					String errorMessage = queryResult.getError() + " getting sample vectors for userSpace: " + userSpace + " chartId: " + chartId.asString() + " on: " + this.databaseName;
					logger.warning(errorMessage);
					throw new ChartRepositoryExecutionException(errorMessage);
				}
				
				ChartRepositoryDAOInfluxImpl.processChartRecordsFromResult(
						queryResult, 
						userSpace, 
						(foundSampleRecord) -> {
							System.out.println(">>>inside lambda<<<<");

						});

			}
			finally
			{
				if(influxDB != null)
				{
					influxDB.close();
				}
			}
		}
		return sampleVectorRecordList;
	}


	
	// =============================================
	// UTILITY METHODS
	// =============================================
	private static InfluxDB getInfluxDBConnection(String host, int port, String username, String password, String databaseName)
	{
		   OkHttpClient.Builder client = new OkHttpClient.Builder()
		            .connectTimeout(1, TimeUnit.MINUTES)
		            .readTimeout(1, TimeUnit.MINUTES)
		            .writeTimeout(1, TimeUnit.MINUTES)
		            .retryOnConnectionFailure(true);
		  // InfluxDB influxdb = InfluxDBFactory.connect(influxUrl, client);		
		
		InfluxDB influxDB = InfluxDBFactory.connect(ChartRepositoryDAOInfluxImpl.getConnectionString(host, port), username, password, client);
		influxDB.enableBatch(1000, 1, TimeUnit.MINUTES);
		influxDB.setLogLevel(InfluxDB.LogLevel.FULL);
		//influxDB.setRetentionPolicy(retentionPolicy);
		//influxDB.setRetentionPolicy("autogen");
		influxDB.setDatabase(databaseName);
		
		Pong pong = influxDB.ping();
		logger.trace("successfully connected to InfluxDB [host: " + host + ", version: " + pong.getVersion() + ", user: " + username + ", databaseName: " + databaseName + "]");
		return influxDB;
	}

	private static String getConnectionString(String host, int port)
	{
		String connectionString = null;
		if(StringUtils.isNotEmpty(host))
		{
			connectionString = "http://" + host + ":" + port;
		}
		return connectionString;
	}
	
	public static String evaluateQuery(String query, Object... args)
	{
		String evaluated = String.format(query, args);
		//TODO: apply escaping here
		return evaluated;
	}
	
	public static String getRetentionPolicy(String retentionPolicy, String database, InfluxDB influxDB)
	{
		if(StringUtils.isNotEmpty(database) && StringUtils.isNotEmpty(retentionPolicy) && influxDB != null)
		{
			logger.trace("fetching retention policies on: " + database);
			QueryResult queryResult = influxDB.query(new Query(ChartRepositoryDAOInfluxImpl.evaluateQuery(QUERY_SHOW_RETENTION_POLICIES, database)));

			if(queryResult == null || queryResult.hasError())
			{
				String errorMessage = queryResult.getError() + " while fetching retention policies on: " + database;
				logger.warning(errorMessage);
				throw new ChartRepositoryExecutionException(errorMessage);
			}
			else
			{
				List<Result> resultsList = queryResult.getResults();
				if(resultsList != null)
				{
					List<Series> seriesList = null;
					List<List<Object>> valuesList = null;
					for(Result result : resultsList)
					{
						seriesList = result.getSeries();
						if(seriesList != null)
						{
							for(Series series : seriesList)
							{
								valuesList = series.getValues();
								List<String> columns = series.getColumns();
								int nameIndex = 0;
								for(int i=0; i<columns.size(); i++)
								{
									if(columns.get(i).equalsIgnoreCase("name"))
									{
										nameIndex = i;
										break;
									}
								}
								
								if(valuesList != null && valuesList.size() > nameIndex)
								{
									for(List<Object> values : valuesList)
									{
										if(values.get(nameIndex) != null && retentionPolicy.equalsIgnoreCase(values.get(nameIndex).toString()))
										{
											return retentionPolicy;
										}
									}
								}
								else
								{
									logger.debug("valuesList is null"); 
								}
							}
						}
						else
						{
							logger.debug("seriesList is null"); 
						}
					}
				}
				else
				{
					logger.debug("resultsList is null"); 
				}
			}
		}
		return null;
	}
	
	public static Set<String> getRetentionPolicies(String database, InfluxDB influxDB)
	{
		Set<String> retentionPolicySet = null;
		if(StringUtils.isNotEmpty(database) && influxDB != null)
		{
			try
			{
				logger.trace("querying retention policy on: " + database);
				QueryResult queryResult = influxDB.query(new Query(ChartRepositoryDAOInfluxImpl.evaluateQuery(QUERY_SHOW_RETENTION_POLICIES, database)));

				if(queryResult != null)
				{
					if(!queryResult.hasError())
					{
						List<Result> resultsList = queryResult.getResults();
						if(resultsList != null)
						{
							List<Series> seriesList = null;
							List<List<Object>> valuesList = null;
							retentionPolicySet = new HashSet<String>();
							for(Result result : resultsList)
							{
								seriesList = result.getSeries();
								if(seriesList != null)
								{
									for(Series series : seriesList)
									{
										valuesList = series.getValues();
										List<String> columns = series.getColumns();
										int nameIndex = 0;
										for(int i=0; i<columns.size(); i++)
										{
											if(columns.get(i).equalsIgnoreCase("name"))
											{
												nameIndex = i;
												break;
											}
										}
										
										if(valuesList != null && valuesList.size() > nameIndex)
										{
											for(List<Object> values : valuesList)
											{
												retentionPolicySet.add(values.get(nameIndex).toString());
											}
										}
										else
										{
											logger.debug("valuesList is null"); 
										}
									}
								}
								else
								{
									logger.debug("seriesList is null"); 
								}
							}
							logger.trace("found " + retentionPolicySet.size() + " retention policies");
						}
						else
						{
							logger.debug("resultsList is null"); 
						}
					}
					else
					{
						logger.warning("queryResult has errors: " + queryResult.getError());
					}
				}
				else
				{
					logger.debug("queryResult is null");
				}			
			}
			catch(Throwable t)
			{
				logger.error("could not write sample vectors", t);
			}
		}
		return retentionPolicySet;
	}	
	
	private static void processChartRecordsFromResult(QueryResult queryResult, String userSpace, Consumer<ChartRecord> chartRecordLambda)
	{
		if(queryResult != null && StringUtils.isNotEmpty(userSpace) && chartRecordLambda != null)
		{
			if(!queryResult.hasError())
			{
				List<Result> resultsList = queryResult.getResults();
				if(resultsList != null)
				{
					List<Series> seriesList = null;
					List<List<Object>> valuesList = null;
					for(Result result : resultsList)
					{
						seriesList = result.getSeries();
						if(seriesList != null)
						{
							for(Series series : seriesList)
							{
								valuesList = series.getValues();
								if(valuesList != null)
								{
									for(List<Object> values : valuesList)
									{
										if(values != null)
										{
											for(Object value : values)
											{
												try
												{
													chartRecordLambda.accept(ChartRecordFactory.createChartRecord(userSpace, value.toString()));
//													chartRecord = ChartRecordFactory.createChartRecord(value.toString());
												}
												catch(ParseException pe)
												{
													logger.debug("could not parse value: " + value.toString() + " into chartRecord");
												}
											}
										}
										else
										{
											logger.debug("values is null"); 
										}
									}
								}
								else
								{
									logger.debug("valuesList is null"); 
								}
							}
						}
						else
						{
							logger.debug("seriesList is null"); 
						}
					}
				}
				else
				{
					logger.debug("resultsList is null"); 
				}
			}
			else
			{
				logger.warning("queryResult has errors: " + queryResult.getError());
			}
		}
		else
		{
			logger.debug("queryResult is null");
		}
	}
	
	
	private static void processSampleRecordsFromResult(QueryResult queryResult, String userSpace, Consumer<SampleRecord> sampleRecordLambda)
	{
		if(queryResult != null && StringUtils.isNotEmpty(userSpace) && sampleRecordLambda != null)
		{
			if(!queryResult.hasError())
			{
				List<Result> resultsList = queryResult.getResults();
				if(resultsList != null)
				{
					List<Series> seriesList = null;
					List<List<Object>> valuesList = null;
					for(Result result : resultsList)
					{
						seriesList = result.getSeries();
						if(seriesList != null)
						{
							for(Series series : seriesList)
							{
								valuesList = series.getValues();
								if(valuesList != null)
								{
									for(List<Object> values : valuesList)
									{
										if(values != null)
										{
											for(Object value : values)
											{
												System.out.println("value=" + value.toString());
//												try
//												{
//													//sampleRecordLambda.accept(new SampleRecord(value.  );
////													chartRecord = ChartRecordFactory.createChartRecord(value.toString());
//												}
//												catch(ParseException pe)
//												{
//													logger.debug("could not parse value: " + value.toString() + " into chartRecord");
//												}
											}
										}
										else
										{
											logger.debug("values is null"); 
										}
									}
								}
								else
								{
									logger.debug("valuesList is null"); 
								}
							}
						}
						else
						{
							logger.debug("seriesList is null"); 
						}
					}
				}
				else
				{
					logger.debug("resultsList is null"); 
				}
			}
			else
			{
				logger.warning("queryResult has errors: " + queryResult.getError());
			}
		}
		else
		{
			logger.debug("queryResult is null");
		}
	}	
	
	private static Set<ChartRecord> getChartRecordsFromResult(QueryResult queryResult, Consumer<String> dbNameAction)
	{
		Set<ChartRecord> resultSet = null;
		if(queryResult != null)
		{
			if(!queryResult.hasError())
			{
				List<Result> resultsList = queryResult.getResults();
				if(resultsList != null)
				{
					resultSet = new HashSet<ChartRecord>();
					List<Series> seriesList = null;
					List<List<Object>> valuesList = null;
					for(Result result : resultsList)
					{
						seriesList = result.getSeries();
						if(seriesList != null)
						{
							for(Series series : seriesList)
							{
								valuesList = series.getValues();
								if(valuesList != null)
								{
									for(List<Object> values : valuesList)
									{
										if(values != null)
										{
											for(Object value : values)
											{
												dbNameAction.accept(value.toString());
												//resultSet.add(ChartRecordFactory.createChartRecord(value.toString()));
											}
										}
										else
										{
											logger.debug("values is null"); 
										}
									}
								}
								else
								{
									logger.debug("valuesList is null"); 
								}
							}
						}
						else
						{
							logger.debug("seriesList is null"); 
						}
					}
				}
				else
				{
					logger.debug("resultsList is null"); 
				}
			}
			else
			{
				logger.warning("queryResult has errors: " + queryResult.getError());
			}
		}
		else
		{
			logger.debug("queryResult is null");
		}
		return resultSet;
	}
	
	private static List<Point> toPoints(ChartId chartId, SampleVectorRecord sampleVectorRecord)
	{
		//every sample with same unit of measure can be captured within the same point
		VParadigmValidator.validateNotNull("chartId", chartId);
		VParadigmValidator.validateNotNull("sampleVector", sampleVectorRecord);

		List<Point> pointList = new ArrayList<Point>();
//		Builder pointBuilder = Point.measurement(chartId.asString()).time(sampleVectorRecord.getGmtTimestamp(),
//				TimeUnit.MILLISECONDS);

		Map<String, List<SampleRecord>> unitOfMeasureSampleDataMap = ChartRepositoryDAOInfluxImpl.groupSampleDataByUnitOfMeasure(sampleVectorRecord);
		if(unitOfMeasureSampleDataMap != null)
		{
			List<SampleRecord> sampleDataList = null;
			Point point = null;
			Builder pointBuilder = null;
			for(String unitOfMeasure : unitOfMeasureSampleDataMap.keySet())
			{
				sampleDataList = unitOfMeasureSampleDataMap.get(unitOfMeasure);
				if(sampleDataList != null)
				{
					pointBuilder = 
							Point
							.measurement(chartId.asString())
							.time(sampleVectorRecord.getGmtTimestamp(),	TimeUnit.MILLISECONDS)
							.tag(TAG_UNIT_OF_MEASURE, unitOfMeasure);
					
					//pointBuilder.tag(TAG_UNIT_OF_MEASURE, unitOfMeasure);
					for(SampleRecord sampleData : sampleDataList)
					{
						pointBuilder.addField(sampleData.getField(), sampleData.getValue());
					}
				}
				//point = pointBuilder.tag(TAG_UNIT_OF_MEASURE, unitOfMeasure).build();
				point = pointBuilder.build();
				logger.trace("adding measurement point for " + point.lineProtocol());
				pointList.add(point);
			}
		}
		
		return pointList;
	}
	
	private static Map<String, List<SampleRecord>> groupSampleDataByUnitOfMeasure(SampleVectorRecord sampleVectorRecord)
	{
		Map<String, List<SampleRecord>> unitOfMeasureSampleDataMap = null;
		if(sampleVectorRecord != null)
		{
			SampleRecord[] sampleDatas = sampleVectorRecord.getSampleRecords();
			if (sampleDatas != null)
			{
				unitOfMeasureSampleDataMap = new HashMap<String, List<SampleRecord>>();
				List<SampleRecord> foundSampleDataList = null;
				for(SampleRecord sampleData : sampleDatas)
				{
					foundSampleDataList = unitOfMeasureSampleDataMap.get(sampleData.getUnitOfMeasure());
					if(foundSampleDataList == null)
					{
						foundSampleDataList = new ArrayList<SampleRecord>();
						unitOfMeasureSampleDataMap.put(sampleData.getUnitOfMeasure(), foundSampleDataList);
					}					
					foundSampleDataList.add(sampleData);
				}
			}
		}
		return unitOfMeasureSampleDataMap;
	}
	
	
	
	
	
	

	
	
//	private static Point toPoint(ChartId chartId, SampleVectorRecord sampleVectorRecord)
//	{
//		VParadigmValidator.validateNotNull("chartId", chartId);
//		VParadigmValidator.validateNotNull("sampleVector", sampleVectorRecord);
//
//		Builder pointBuilder = Point.measurement(chartId.asString()).time(sampleVectorRecord.getGmtTimestamp(),
//				TimeUnit.MILLISECONDS);
//
//		SampleRecord[] sampleDatas = sampleVectorRecord.getSampleData();
//		if (sampleDatas != null)
//		{
//			for(SampleRecord sampleData : sampleDatas)
//			{
//				pointBuilder.addField(sampleData.getField(), sampleData.getValue());
//			}
//			pointBuilder.tag(TAG_UNIT_OF_MEASURE, sampleVectorRecord.getUnitOfMeasure());
//		}
//		Point p = pointBuilder.build();
//		logger.trace("adding measurement point for " + p.lineProtocol());
//		return p;
//		// return pointBuilder.build();
//	}

	
//	@Override
//	public ChartRecord saveAggregateTimeSeries(String name, SampleFrequency sampleFrequency)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ChartRecord getAggregateTimeSeries(GUID aggregateTimeSeriesGUID)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Collection<ChartRecord> findAggregateTimeSeries()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean saveSampleVector(ChartRecord aggregateTimeSeries, SampleVector sampleVector)
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
//	public boolean saveSampleVectors(ChartRecord aggregateTimeSeries, List<SampleVector> sampleVectors)
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
//		SampleRecord[] sampleDatas = sampleVector.getSampleData();
//		if(sampleDatas != null)
//		{
//			for(SampleRecord sampleData : sampleDatas)
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
	
	public static void main(String[] args)
	{
		
		ChartRepositoryDAOInfluxImpl repo = new ChartRepositoryDAOInfluxImpl("localhost", 8086, "test", "test", "fintrader_instrument");
		repo.createChart("myretention", SampleRecordFrequency.DAY, "aapl", "mychart");
		
//		try
//		{
//			final String serverURL = "http://localhost:8086", username = "test", password = "test";
//			final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);
//			
//			influxDB.setDatabase("fintrader_instrument");
//			influxDB.setRetentionPolicy(RETENTION_POLICY_AUTOGEN);
//			//influxDB.enableBatch(BatchOptions.DEFAULTS);
//			influxDB.enableBatch(100, 5000, TimeUnit.MILLISECONDS);
//
//			
//			BatchPoints batchPoints = BatchPoints.database("fintrader_instrument").retentionPolicy(RETENTION_POLICY_AUTOGEN).build();
//			
//			Builder pointBuilder = Point.measurement("h2o_feet").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
//			pointBuilder.addField("level description", "below 5 feet");
//			pointBuilder.addField("water_level", 4.0d);
//			pointBuilder.tag(TAG_UNIT_OF_MEASURE, "someunitofmeasure");
//			Point p1 = pointBuilder.build();
//			
//			batchPoints.point(p1);
//			
//			influxDB.write(batchPoints);
//			influxDB.flush();
//			
//			
//
//			
////			influxDB.write(Point.measurement("h2o_feet")
////				    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
////				    .tag("location", "santa_monica")
////				    .addField("level description", "below 3 feet")
////				    .addField("water_level", 2.064d)
////				    .build());
//			influxDB.close();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	}


	
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