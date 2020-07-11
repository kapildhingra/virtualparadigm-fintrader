package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ChartLoaderCommand
{
	SYMBOLS("symbols", "symbols"),
	PULL("pull", "pull"),
	LOAD("load", "load"),
	QUERY("query", "query"),
	PEEK("peek", "peek");
//	SYMBOLS("symbols", "symbols"),
//	INSTRUMENT("instrument", "instrument"),
//	CHART("chart", "chart"),
//	LOAD("load", "load");
	
	private static final Map<String, ChartLoaderCommand> lookupMap = new HashMap<String, ChartLoaderCommand>();
	static
	{
		for(ChartLoaderCommand command : EnumSet.allOf(ChartLoaderCommand.class))
		{
			lookupMap.put(command.getName(), command);
		}
	}
	
	private ChartLoaderCommand(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
	
	private String name;
	private String description;
	
	public String getName()
	{
		return name;
	}
	public String getDescription()
	{
		return description;
	}
	public static ChartLoaderCommand get(String strInstrumentLoaderCommand)
	{
		return lookupMap.get(strInstrumentLoaderCommand);
	}
}