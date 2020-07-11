package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.time.LocalDateTime;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.virtualparadigm.fintrader.tool.chartloader.delegate.ChartVectorVO;
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
				case SYMBOLS:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildSymbolsCommandLineOptions();
					break;
				}
				case PULL:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPullCommandLineOptions();
					break;
				}
				case LOAD:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildLoadCommandLineOptions();
					break;
				}
				case QUERY:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildQueryCommandLineOptions();
					break;
				}
				case PEEK:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildPeekCommandLineOptions();
					break;
				}
				default:
				{
					cliOptions = ChartLoaderOptionsBuilder.buildSymbolsCommandLineOptions();
				}
			}
		}
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
	
	private static Options buildPullCommandLineOptions()
	{
		Options cliOptions = new Options();
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.EXCHANGE.getShortName())
					.longOpt(ChartLoaderOption.EXCHANGE.getLongName())
					.desc(ChartLoaderOption.EXCHANGE.getDescription())
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
		return cliOptions;
	}
	
	private static Options buildLoadCommandLineOptions()
	{
		Options cliOptions = new Options();
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.USERSPACE.getShortName())
					.longOpt(ChartLoaderOption.USERSPACE.getLongName())
					.desc(ChartLoaderOption.USERSPACE.getDescription())
					.hasArg()
					//.required()
					.build());
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.CHART_NAME.getShortName())
					.longOpt(ChartLoaderOption.CHART_NAME.getLongName())
					.desc(ChartLoaderOption.CHART_NAME.getDescription())
					.hasArg()
					//.required()
					.build());
		cliOptions.addOption(
				Option.builder(
					ChartLoaderOption.EXCHANGE.getShortName())
					.longOpt(ChartLoaderOption.EXCHANGE.getLongName())
					.desc(ChartLoaderOption.EXCHANGE.getDescription())
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
					ChartLoaderOption.EXCHANGE.getShortName())
					.longOpt(ChartLoaderOption.EXCHANGE.getLongName())
					.desc(ChartLoaderOption.EXCHANGE.getDescription())
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
		return cliOptions;
	}
	
	
	private static Options buildPeekCommandLineOptions()
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
	
	
	public static void main(String[] args)
	{
		System.out.println(LocalDateTime.parse("1998-01-18T00:00:00.000", FormatUtil.DATE_TIME_FORMATTER));
	}
	
}