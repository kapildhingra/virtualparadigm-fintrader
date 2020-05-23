package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartData;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.FormatUtil;
import com.virtualparadigm.fintrader.tool.chartloader.delegate.Instrument;
import com.virtualparadigm.fintrader.tool.chartloader.process.ChartLoader;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

public class Main
{
	private static final VParadigmLogger logger = new VParadigmLogger(Main.class);
    
	
	public static void main(String[] args)
	{
		if(args.length > 0)
		{
			CommandLineParser cliParser = new DefaultParser();
			CommandLine cmd = null;
			try
			{
				Options options = Main.buildCommandLineOptions(args[0]);
				if(options != null)
				{
					cmd = cliParser.parse(options, args);
				}
			}
			catch(ParseException pe)
			{
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
						ApplicationContext context = new ClassPathXmlApplicationContext( "instrument-loader-context.xml" );
						ChartLoader chartLoader = context.getBean(ChartLoader.class);
						switch(instrumentLoaderCommand)
						{
							case SYMBOLS:
							{
								List<Instrument> instruments = chartLoader.getInstruments();
								if(instruments != null)
								{
									String strOutputFile = cmd.getOptionValue(ChartLoaderOption.OUTPUT_FILE.getLongName());
									if(StringUtils.isEmpty(strOutputFile))
									{
										for(Instrument instrument : instruments)
										{
											logger.info("EXCHANGE: " + instrument.getExchange() + " SYMBOL: " + instrument.getSymbol());
										}
									}
									else
									{
										File outputFile = new File(strOutputFile);
										logger.info("writing to file: " + strOutputFile);
										if(strOutputFile.endsWith(".csv"))
										{
											StringBuffer outputBuffer = new StringBuffer();
											outputBuffer.append("Exchange|Symbol\n");
											for(Instrument instrument : instruments)
											{
												outputBuffer.append(instrument.getExchange());
												outputBuffer.append(",");
												outputBuffer.append(instrument.getSymbol());
												outputBuffer.append("\n");
											}
											
											FileUtils.writeStringToFile(outputFile, outputBuffer.toString());
										}
										else
										{
											ObjectMapper mapper = new ObjectMapper();
										    mapper.writeValue(outputFile, instruments);											
										}
										logger.info("done writing file");
									}
								}
								break;
							}
							case CHART:
							{
								String strSymbol = cmd.getOptionValue(ChartLoaderOption.SYMBOL.getLongName());
								SampleDTOFrequency sampleFrequency = SampleDTOFrequency.valueOf(cmd.getOptionValue(ChartLoaderOption.SAMPLE_FREQUENCY.getLongName()));
								String strStartTime = cmd.getOptionValue(ChartLoaderOption.START_TIME.getLongName());
								String strEndTime = cmd.getOptionValue(ChartLoaderOption.END_TIME.getLongName());
								LocalDateTime startTime = null;
								LocalDateTime endTime = null;
								if(!StringUtils.isEmpty(strStartTime))
								{
									//startTime = Main.DATE_TIME_FORMATTER.parseBest(strStartTime, ZonedDateTime::from, LocalDateTime::from, LocalDate::from);
									startTime = LocalDateTime.parse(strStartTime, FormatUtil.DATE_TIME_FORMATTER);
								}
								if(!StringUtils.isEmpty(strEndTime))
								{
									endTime = LocalDateTime.parse(strEndTime, FormatUtil.DATE_TIME_FORMATTER);
								}
								String strOutputFile = cmd.getOptionValue(ChartLoaderOption.OUTPUT_FILE.getLongName());
						
								List<ChartData> instrumentDataList = chartLoader.getChartData(strSymbol, sampleFrequency, startTime, endTime);

								if(StringUtils.isEmpty(strOutputFile))
								{
									for(ChartData instrumentData : instrumentDataList)
									{
										System.out.println(instrumentData.getDateTime().format(FormatUtil.DATE_TIME_FORMATTER) + "," + instrumentData.getOpen() + "," + instrumentData.getHigh() + "," + instrumentData.getLow() + "," + instrumentData.getClose() + "," + instrumentData.getVolume());
									}
								}
								else
								{
									File outputFile = new File(strOutputFile);
									logger.info("writing to file: " + strOutputFile);
									if(strOutputFile.endsWith(".csv"))
									{
										StringBuffer outputBuffer = new StringBuffer();
										outputBuffer.append("datetime,open,high,low,close,volume\n");
										for(ChartData instrumentData : instrumentDataList)
										{
											outputBuffer.append(instrumentData.getDateTime().format(FormatUtil.DATE_TIME_FORMATTER));
											outputBuffer.append(",");
											outputBuffer.append(instrumentData.getOpen());
											outputBuffer.append(",");
											outputBuffer.append(instrumentData.getHigh());
											outputBuffer.append(",");
											outputBuffer.append(instrumentData.getLow());
											outputBuffer.append(",");
											outputBuffer.append(instrumentData.getClose());
											outputBuffer.append(",");
											outputBuffer.append(instrumentData.getVolume());
											outputBuffer.append("\n");
										}
										FileUtils.writeStringToFile(outputFile, outputBuffer.toString());
									}
									else
									{
										ObjectMapper mapper = new ObjectMapper();
									    mapper.writeValue(outputFile, instrumentDataList);											
									}
									logger.info("done writing file");
								}
								break;
							}							
							case LOAD:
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
	}
	
	
	
	private static Options buildCommandLineOptions(String command)
	{
		Options cliOptions = null;
		ChartLoaderCommand instrumentLoaderCommand = ChartLoaderCommand.get(command);
		if(instrumentLoaderCommand != null)
		{
			switch(instrumentLoaderCommand)
			{
				case SYMBOLS:
				{
					cliOptions = Main.buildSymbolsCommandLineOptions();
					break;
				}
				case CHART:
				{
					cliOptions = Main.buildChartCommandLineOptions();
					break;
				}
				case LOAD:
				{
					cliOptions = Main.buildLoadCommandLineOptions();
					break;
				}
				
				default:
				{
					cliOptions = Main.buildSymbolsCommandLineOptions();
				}
			}
		}
		return cliOptions;
	}
	
	private static Options buildLoadCommandLineOptions()
	{
		Options cliOptions = new Options();
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.INPUT_FILE.getShortName())
					.longOpt(ChartLoaderOption.INPUT_FILE.getLongName())
					.desc(ChartLoaderOption.INPUT_FILE.getDescription())
					.hasArg()
					.build());
		
		return cliOptions;
	}
	private static Options buildSymbolsCommandLineOptions()
	{
		Options cliOptions = new Options();
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.OUTPUT_FILE.getShortName())
					.longOpt(ChartLoaderOption.OUTPUT_FILE.getLongName())
					.desc(ChartLoaderOption.OUTPUT_FILE.getDescription())
					.hasArg()
					.build());
		return cliOptions;
	}
	
	private static Options buildChartCommandLineOptions()
	{
		Options cliOptions = new Options();
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.SYMBOL.getShortName())
					.longOpt(ChartLoaderOption.SYMBOL.getLongName())
					.desc(ChartLoaderOption.SYMBOL.getDescription())
					.hasArg()
					.required()
					.build());
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.SAMPLE_FREQUENCY.getShortName())
					.longOpt(ChartLoaderOption.SAMPLE_FREQUENCY.getLongName())
					.desc(ChartLoaderOption.SAMPLE_FREQUENCY.getDescription())
					.hasArg()
					.required()
					.build());
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.START_TIME.getShortName())
					.longOpt(ChartLoaderOption.START_TIME.getLongName())
					.desc(ChartLoaderOption.START_TIME.getDescription())
					.hasArg()
					.build());
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.END_TIME.getShortName())
					.longOpt(ChartLoaderOption.END_TIME.getLongName())
					.desc(ChartLoaderOption.END_TIME.getDescription())
					.hasArg()
					.build());
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.OUTPUT_FILE.getShortName())
					.longOpt(ChartLoaderOption.OUTPUT_FILE.getLongName())
					.desc(ChartLoaderOption.OUTPUT_FILE.getDescription())
					.hasArg()
					.build());
		return cliOptions;
	}
	
	private static void printUnsupportedCommandError()
	{
		logger.error("unsupported command");
		
	}
	
	private static void helpMenu(String command)
	{
		Options opt = buildCommandLineOptions(command);
		HelpFormatter f = new HelpFormatter();
		f.printHelp("ChartLoader CLI", opt);
	}
	
	
}