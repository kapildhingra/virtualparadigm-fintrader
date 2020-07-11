package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ChartLoaderOption
{
	USERSPACE("u", "userspace", "userspace", true), 
	CHART_NAME("n", "chartname", "chartname", true), 
	EXCHANGE("e", "exchange", "exchange", true), 
	SYMBOLS("ss", "symbols", "symbols", false), 
	SYMBOL("s", "symbol", "symbol", true), 
	SAMPLE_FREQUENCY("f", "frequency", "frequency", true), 
	START_TIME("st", "start", "start", true), 
	END_TIME("et", "end", "end", true),
	INPUT_FILE("i", "input", "input", true),
	OUTPUT_FILE("o", "output", "output", true);
	
	private static final Map<String, ChartLoaderOption> lookupMap = new HashMap<String, ChartLoaderOption>();
	static
	{
		for(ChartLoaderOption command : EnumSet.allOf(ChartLoaderOption.class))
		{
			lookupMap.put(command.getLongName(), command);
		}
	}
	
	private ChartLoaderOption(String shortName, String longName, String description, boolean argument)
	{
		this.shortName = shortName;
		this.longName = longName;
		this.argument = argument;
		this.description = description;
	}

	private String shortName;
	private String longName;
	private boolean argument;
	private String description;
	
	public String getShortName()
	{
		return shortName;
	}

	public String getLongName()
	{
		return longName;
	}

	public boolean hasArgument()
	{
		return argument;
	}

	public String getDescription()
	{
		return description;
	}

	public static ChartLoaderOption get(String strInstrumentLoaderOption)
	{
		return lookupMap.get(strInstrumentLoaderOption);
	}
}