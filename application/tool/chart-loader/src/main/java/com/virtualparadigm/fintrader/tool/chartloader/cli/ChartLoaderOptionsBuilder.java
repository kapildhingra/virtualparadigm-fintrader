package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.time.LocalDateTime;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.virtualparadigm.fintrader.tool.chartloader.util.FormatUtil;

public class ChartLoaderOptionsBuilder
{
	public static Options buildCommandLineOptions(String command)
	{
		Options cliOptions = null;
		ChartLoaderCommand instrumentLoaderCommand = ChartLoaderCommand.get(command);
		if(instrumentLoaderCommand != null)
		{
			switch(instrumentLoaderCommand)
			{
				case PULL_MARKETS:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPullMarketsCommandLineOptions();
					break;
				}
				case PULL_SYMBOLS:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPullSymbolsCommandLineOptions();
					break;
				}
				case PULL_CHART:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPullChartCommandLineOptions();
					break;
				}
				case PEEK_MARKETS:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPeekMarketsCommandLineOptions();
					break;
				}
				case PEEK_SYMBOLS:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPeekSymbolsCommandLineOptions();
					break;
				}
				case PEEK_CHART:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPeekChartCommandLineOptions();
					break;
				}
				case LOAD_CHART:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildLoadChartCommandLineOptions();
					break;
				}
				case QUERY_CHART:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildQueryCommandLineOptions();
					break;
				}
				default:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPullMarketsCommandLineOptions();
				}
			}
		}
		return cliOptions;
	}
	
	private static Options buildPullMarketsCommandLineOptions()
	{
		Options cliOptions = new Options();
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.OUTPUT_FILE.getShortName())
					.longOpt(ChartLoaderOption.OUTPUT_FILE.getLongName())
					.desc(ChartLoaderOption.OUTPUT_FILE.getDescription())
					.hasArg()
					.build());
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.JSON_FORMAT.getShortName())
					.longOpt(ChartLoaderOption.JSON_FORMAT.getLongName())
					.desc(ChartLoaderOption.JSON_FORMAT.getDescription())
					.build());
		
		return cliOptions;
	}
	
	private static Options buildPullSymbolsCommandLineOptions()
	{
		Options cliOptions = new Options();
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.OUTPUT_FILE.getShortName())
					.longOpt(ChartLoaderOption.OUTPUT_FILE.getLongName())
					.desc(ChartLoaderOption.OUTPUT_FILE.getDescription())
					.hasArg()
					.build());
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.JSON_FORMAT.getShortName())
					.longOpt(ChartLoaderOption.JSON_FORMAT.getLongName())
					.desc(ChartLoaderOption.JSON_FORMAT.getDescription())
					.build());
		
		return cliOptions;
	}
	
	private static Options buildPullChartCommandLineOptions()
	{
		Options cliOptions = new Options();
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.MARKET.getShortName())
					.longOpt(ChartLoaderOption.MARKET.getLongName())
					.desc(ChartLoaderOption.MARKET.getDescription())
					.hasArg()
					.required()
					.build());
		
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
		
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.JSON_FORMAT.getShortName())
					.longOpt(ChartLoaderOption.JSON_FORMAT.getLongName())
					.desc(ChartLoaderOption.JSON_FORMAT.getDescription())
					.build());
		
		return cliOptions;
	}
	
	private static Options buildPeekMarketsCommandLineOptions()
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
	
	private static Options buildPeekSymbolsCommandLineOptions()
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
	
	private static Options buildPeekChartCommandLineOptions()
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
	
	private static Options buildLoadChartCommandLineOptions()
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
	
	
	private static Options buildQueryCommandLineOptions()
	{
		Options cliOptions = new Options();
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.MARKET.getShortName())
					.longOpt(ChartLoaderOption.MARKET.getLongName())
					.desc(ChartLoaderOption.MARKET.getDescription())
					.hasArg()
					//.required()
					.build());
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
	
//	private static Options buildLoadCommandLineOptions()
//	{
//		Options cliOptions = new Options();
//		cliOptions.addOption(
//				Option.builder(
//					ChartLoaderOption.USERSPACE.getShortName())
//					.longOpt(ChartLoaderOption.USERSPACE.getLongName())
//					.desc(ChartLoaderOption.USERSPACE.getDescription())
//					.hasArg()
//					//.required()
//					.build());
//		cliOptions.addOption(
//				Option.builder(
//					ChartLoaderOption.CHART_NAME.getShortName())
//					.longOpt(ChartLoaderOption.CHART_NAME.getLongName())
//					.desc(ChartLoaderOption.CHART_NAME.getDescription())
//					.hasArg()
//					//.required()
//					.build());
//		cliOptions.addOption(
//				Option.builder(
//					ChartLoaderOption.MARKET.getShortName())
//					.longOpt(ChartLoaderOption.MARKET.getLongName())
//					.desc(ChartLoaderOption.MARKET.getDescription())
//					.hasArg()
//					.required()
//					.build());
//		cliOptions.addOption(
//				Option.builder(
//					ChartLoaderOption.SYMBOL.getShortName())
//					.longOpt(ChartLoaderOption.SYMBOL.getLongName())
//					.desc(ChartLoaderOption.SYMBOL.getDescription())
//					.hasArg()
//					.required()
//					.build());
//		cliOptions.addOption(
//				Option.builder(
//					ChartLoaderOption.SAMPLE_FREQUENCY.getShortName())
//					.longOpt(ChartLoaderOption.SAMPLE_FREQUENCY.getLongName())
//					.desc(ChartLoaderOption.SAMPLE_FREQUENCY.getDescription())
//					.hasArg()
//					.required()
//					.build());
//		cliOptions.addOption(
//				Option.builder(
//					ChartLoaderOption.INPUT_FILE.getShortName())
//					.longOpt(ChartLoaderOption.INPUT_FILE.getLongName())
//					.desc(ChartLoaderOption.INPUT_FILE.getDescription())
//					.hasArg()
//					.build());
//		return cliOptions;
//	}	
	
	
	public static void main(String[] args)
	{
		System.out.println(LocalDateTime.parse("1998-01-18T00:00:00.000", FormatUtil.DATE_TIME_FORMATTER));
	}
	
}