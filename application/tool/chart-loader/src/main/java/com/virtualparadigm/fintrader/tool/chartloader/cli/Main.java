package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartVectorVO;
import com.virtualparadigm.fintrader.tool.chartloader.process.ChartCLO;
import com.virtualparadigm.fintrader.tool.chartloader.process.ChartLoader;
import com.virtualparadigm.fintrader.tool.chartloader.process.ChartVectorCLO;
import com.virtualparadigm.fintrader.tool.chartloader.process.InstrumentCLO;
import com.virtualparadigm.fintrader.tool.chartloader.process.MarketCLO;
import com.virtualparadigm.fintrader.tool.chartloader.process.SampleCLOFrequency;
import com.virtualparadigm.fintrader.tool.chartloader.util.FormatUtil;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

public class Main
{
	private static final VParadigmLogger logger = new VParadigmLogger(Main.class);
	private static final String CSV_DELIMITER = ",";
	private static final String FIELD_VALUE_DELIMITER = ":";
    
	
	public static void main(String[] args)
	{
		if(args.length > 0)
		{
			CommandLineParser cliParser = new DefaultParser();
			CommandLine cmd = null;
			try
			{
				Options options = ChartLoaderOptionsBuilder.buildCommandLineOptions(args[0]);
				if(options != null)
				{
					cmd = cliParser.parse(options, args);
				}
			}
			catch(ParseException pe)
			{
				logger.error("error parsing command", pe);
				Main.printUnsupportedCommandError();
			}
			
			
			if(cmd != null)
			{
				ChartLoaderCommand instrumentLoaderCommand = ChartLoaderCommand.get(args[0]);
				if(instrumentLoaderCommand != null)
				{
					try
					{
						@SuppressWarnings("resource")
						ApplicationContext context = new ClassPathXmlApplicationContext( "chart-loader-context.xml" );
						ChartLoader chartLoader = context.getBean(ChartLoader.class);
						switch(instrumentLoaderCommand)
						{
							case PULL_MARKETS:
							{
								List<MarketCLO> marketCLOs = chartLoader.pullMarkets();
								if(marketCLOs != null)
								{
									boolean isJsonFormat = cmd.hasOption(ChartLoaderOption.JSON_FORMAT.getLongName());
									String strOutputFile = cmd.getOptionValue(ChartLoaderOption.OUTPUT_FILE.getLongName());
									if(StringUtils.isEmpty(strOutputFile))
									{
										if(isJsonFormat)
										{
											ObjectMapper mapper = new ObjectMapper();
										    mapper.writeValue(System.out, marketCLOs);	
										}
										for(MarketCLO marketCLO : marketCLOs)
										{
											System.out.println("mic:" + marketCLO.getMarketIdentifierCode() + ", timezone:" + marketCLO.getDateTimeZone());
										}
									}
									else
									{
										File outputFile = new File(strOutputFile);
										logger.info("writing to file: " + strOutputFile);
										if(strOutputFile.endsWith(".csv"))
										{
											StringBuffer outputBuffer = new StringBuffer();
											outputBuffer.append("MIC" + CSV_DELIMITER + "Timezone\n");
											for(MarketCLO marketCLO : marketCLOs)
											{
												outputBuffer.append(marketCLO.getMarketIdentifierCode());
												outputBuffer.append(",");
												outputBuffer.append(marketCLO.getDateTimeZone());
												outputBuffer.append("\n");
											}
											
											FileUtils.writeStringToFile(outputFile, outputBuffer.toString());
										}
										else
										{
											ObjectMapper mapper = new ObjectMapper();
										    mapper.writeValue(outputFile, marketCLOs);											
										}
										logger.info("done writing file");
									}
								}
								break;
							}
							case PULL_SYMBOLS:
							{
								List<InstrumentCLO> instrumentCLOs = chartLoader.pullInstruments();
								if(instrumentCLOs != null)
								{
									String strOutputFile = cmd.getOptionValue(ChartLoaderOption.OUTPUT_FILE.getLongName());
									boolean isJsonFormat = cmd.hasOption(ChartLoaderOption.JSON_FORMAT.getLongName());
									
									if(StringUtils.isEmpty(strOutputFile))
									{
										if(isJsonFormat)
										{
											ObjectMapper mapper = new ObjectMapper();
										    mapper.writeValue(System.out, instrumentCLOs);
										}
										for(InstrumentCLO instrument : instrumentCLOs)
										{
											System.out.println("mic:" + instrument.getMic() + ", symbol:" + instrument.getSymbol());
										}
									}
									else
									{
										File outputFile = new File(strOutputFile);
										logger.info("writing to file: " + strOutputFile);
										if(strOutputFile.endsWith(".csv"))
										{
											StringBuffer outputBuffer = new StringBuffer();
											outputBuffer.append("Exchange" + CSV_DELIMITER + "Symbol\n");
											for(InstrumentCLO instrument : instrumentCLOs)
											{
												outputBuffer.append(instrument.getMic());
												outputBuffer.append(",");
												outputBuffer.append(instrument.getSymbol());
												outputBuffer.append("\n");
											}
											
											FileUtils.writeStringToFile(outputFile, outputBuffer.toString());
										}
										else
										{
											ObjectMapper mapper = new ObjectMapper();
										    mapper.writeValue(outputFile, instrumentCLOs);											
										}
										logger.info("done writing file");
									}
								}
								break;
							}
							case PULL_CHART:
							{
								String strExchange = cmd.getOptionValue(ChartLoaderOption.MARKET.getLongName());
								String strSymbol = cmd.getOptionValue(ChartLoaderOption.SYMBOL.getLongName());
								SampleCLOFrequency sampleFrequency = SampleCLOFrequency.valueOf(cmd.getOptionValue(ChartLoaderOption.SAMPLE_FREQUENCY.getLongName()).toUpperCase());
								String strStartTime = cmd.getOptionValue(ChartLoaderOption.START_TIME.getLongName());
								String strEndTime = cmd.getOptionValue(ChartLoaderOption.END_TIME.getLongName());
								LocalDateTime startTime = null;
								LocalDateTime endTime = null;
								if(!StringUtils.isEmpty(strStartTime))
								{
									startTime = LocalDateTime.parse(strStartTime, FormatUtil.DATE_TIME_FORMATTER);
								}
								if(!StringUtils.isEmpty(strEndTime))
								{
									endTime = LocalDateTime.parse(strEndTime, FormatUtil.DATE_TIME_FORMATTER);
								}
								String strOutputFile = cmd.getOptionValue(ChartLoaderOption.OUTPUT_FILE.getLongName());
						
								ChartCLO chartCLO = chartLoader.pullChart(strExchange, strSymbol, sampleFrequency, startTime, endTime);

								if(StringUtils.isEmpty(strOutputFile))
								{
									StringBuffer strBuf = null;
									Map<String, BigDecimal> valueMap = null;
									for(ChartVectorCLO chartVectorCLO : chartCLO.getChartVectorCLOList())
									{
										strBuf = new StringBuffer();
										strBuf.append(ChartVectorVO.FIELD.DATETIME.name());
										strBuf.append(Main.FIELD_VALUE_DELIMITER);
										strBuf.append(chartVectorCLO.getDateTime().format(FormatUtil.DATE_TIME_FORMATTER));
										
										valueMap = chartVectorCLO.getValues();
										if(valueMap != null && valueMap.size() > 0)
										{
											for(String key : valueMap.keySet())
											{
												strBuf.append(Main.CSV_DELIMITER);
												strBuf.append(" ");
												strBuf.append(key);
												strBuf.append(Main.FIELD_VALUE_DELIMITER);
												strBuf.append(valueMap.get(key));
											}
										}
										System.out.println(strBuf.toString());
									}
								}
								else
								{
									File outputFile = new File(strOutputFile);
									logger.info("writing to file: " + strOutputFile);
									if(strOutputFile.endsWith(".csv"))
									{
										StringBuffer outputBuffer = new StringBuffer();
										outputBuffer.append(ChartVectorVO.FIELD.DATETIME);
										outputBuffer.append(CSV_DELIMITER);
										outputBuffer.append(ChartVectorVO.FIELD.OPEN);
										outputBuffer.append(CSV_DELIMITER);
										outputBuffer.append(ChartVectorVO.FIELD.HIGH);
										outputBuffer.append(CSV_DELIMITER);
										outputBuffer.append(ChartVectorVO.FIELD.LOW);
										outputBuffer.append(CSV_DELIMITER);
										outputBuffer.append(ChartVectorVO.FIELD.CLOSE);
										outputBuffer.append(CSV_DELIMITER);
										outputBuffer.append(ChartVectorVO.FIELD.VOLUME);
										outputBuffer.append("\n");

										for(ChartVectorCLO chartVectorCLO : chartCLO.getChartVectorCLOList())
										{
											outputBuffer.append(chartVectorCLO.getDateTime().format(FormatUtil.DATE_TIME_FORMATTER));
											outputBuffer.append(CSV_DELIMITER);
											outputBuffer.append(chartVectorCLO.getOpen());
											outputBuffer.append(CSV_DELIMITER);
											outputBuffer.append(chartVectorCLO.getHigh());
											outputBuffer.append(CSV_DELIMITER);
											outputBuffer.append(chartVectorCLO.getLow());
											outputBuffer.append(CSV_DELIMITER);
											outputBuffer.append(chartVectorCLO.getClose());
											outputBuffer.append(CSV_DELIMITER);
											outputBuffer.append(chartVectorCLO.getVolume());
											outputBuffer.append("\n");
										}
										FileUtils.writeStringToFile(outputFile, outputBuffer.toString());
									}
									else
									{
										ObjectMapper mapper = new ObjectMapper();
									    mapper.writeValue(outputFile, chartCLO);
									}
									logger.info("done writing file");
								}
								break;
							}
							case LOAD_CHART:
							{
								String strUserSpace = cmd.getOptionValue(ChartLoaderOption.USERSPACE.getLongName());
								String strChartName = cmd.getOptionValue(ChartLoaderOption.CHART_NAME.getLongName());
								String strExchange = cmd.getOptionValue(ChartLoaderOption.MARKET.getLongName());
								String strSymbol = cmd.getOptionValue(ChartLoaderOption.SYMBOL.getLongName());
								SampleCLOFrequency sampleDataFrequency = SampleCLOFrequency.valueOf(cmd.getOptionValue(ChartLoaderOption.SAMPLE_FREQUENCY.getLongName()).toUpperCase());
								String strInputFile = cmd.getOptionValue(ChartLoaderOption.INPUT_FILE.getLongName());
								List<ChartVectorCLO> chartVectorDataList = new ArrayList<ChartVectorCLO>();
								
								if(StringUtils.isNotEmpty(strInputFile))
								{
									File inputFile = new File(strInputFile);
									logger.info("reading from file: " + strInputFile);									
									if(strInputFile.endsWith(".csv"))
									{
										Reader reader = new FileReader(inputFile);
										Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
//										Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ChartVectorData.FIELD.class).parse(reader);
										//Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
										for(CSVRecord record : records)
										{
											chartVectorDataList.add(
													new ChartVectorCLO(
															LocalDateTime.parse(record.get(ChartVectorVO.FIELD.DATETIME.name()), FormatUtil.DATE_TIME_FORMATTER), 
															new BigDecimal(record.get(ChartVectorVO.FIELD.OPEN.name())), 
															new BigDecimal(record.get(ChartVectorVO.FIELD.HIGH.name())), 
															new BigDecimal(record.get(ChartVectorVO.FIELD.LOW.name())), 
															new BigDecimal(record.get(ChartVectorVO.FIELD.CLOSE.name())),  
															new BigDecimal(record.get(ChartVectorVO.FIELD.VOLUME.name()))));
										}
									}
									else
									{
										ObjectMapper mapper = new ObjectMapper();
										chartVectorDataList = (List<ChartVectorCLO>)mapper.readValue(strInputFile, chartVectorDataList.getClass());
									}
								}
								else
								{
//									BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//									String strLine = null;
//									String[] values = null;
//									while((strLine = reader.readLine()) != null)
//									{
//										values = strLine.trim().split(",");
//										//don't ingest unless all values are present
//										if(values != null && values.length == 6)
//										{
//											//assuming format: DATETIME:20100101T00:00:00, OPEN:100, HIGH:102, LOW:98: CLOSE:101, VOLUME:100000
//											chartVectorDataList.add(
//													new ChartVectorData(
//															LocalDateTime.parse(values[0].trim().substring(values[0].indexOf(FIELD_VALUE_DELIMITER)+1).trim(), FormatUtil.DATE_TIME_FORMATTER), 
//															new BigDecimal(values[1].trim().substring(values[1].indexOf(FIELD_VALUE_DELIMITER)+1).trim()), 
//															new BigDecimal(values[2].trim().substring(values[2].indexOf(FIELD_VALUE_DELIMITER)+1).trim()), 
//															new BigDecimal(values[3].trim().substring(values[3].indexOf(FIELD_VALUE_DELIMITER)+1).trim()), 
//															new BigDecimal(values[4].trim().substring(values[4].indexOf(FIELD_VALUE_DELIMITER)+1).trim()), 
//															new BigDecimal(values[5].trim().substring(values[5].indexOf(FIELD_VALUE_DELIMITER)+1).trim())));
//										}
//									}
								}
								
								chartLoader.loadChart(strUserSpace, strChartName, strExchange, strSymbol, sampleDataFrequency, chartVectorDataList);
								logger.info("done loading");

								break;
							}
							case QUERY_CHART:
							{
								
								break;
							}							
							case PEEK_CHART:
							{
								
								break;
							}							
							default:
							{
								Main.printUnsupportedCommandError();
							}
						}
					}
					catch(Throwable t)
					{
						t.printStackTrace();
					}
				}
				else
				{
					Main.printUnsupportedCommandError();
				}
			}
			else
			{
				Main.printUnsupportedCommandError();
			}
		}
		else
		{
			Main.printUnsupportedCommandError();
		}
		logger.info("exiting...");

	}
	
	
	

	
	private static void printUnsupportedCommandError()
	{
		logger.error("unsupported command");
		
	}
	
	private static void helpMenu(String command)
	{
		Options opt = ChartLoaderOptionsBuilder.buildCommandLineOptions(command);
		HelpFormatter f = new HelpFormatter();
		f.printHelp("ChartLoader CLI", opt);
	}
	
	private ChartVectorVO parseLine(String instrumentDataLine)
	{
		ChartVectorVO instrumentData = null;
		if(StringUtils.isNotEmpty(instrumentDataLine))
		{
			String[] values = instrumentDataLine.split(",");
			if(values != null && values.length == 6)
			{
//				for(int i=0; i<values.length; i++)
			}
		}
		
		return instrumentData;
	}
	
	
}