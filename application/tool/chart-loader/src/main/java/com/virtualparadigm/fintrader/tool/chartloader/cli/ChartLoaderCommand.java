package com.virtualparadigm.fintrader.tool.chartloader.cli;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ChartLoaderCommand
{
	PULL_MARKETS("pull-markets", "pull-markets"),
	PULL_SYMBOLS("pull-symbols", "pull-symbols"),
	PULL_CHART("pull-chart", "pull-chart"),
	PEEK_MARKETS("peek-markets", "peek-markets"),
	PEEK_SYMBOLS("peek-symbols", "peek-symbols"),
	PEEK_CHART("peek-chart", "peek-chart"),
	LOAD_CHART("load-chart", "load-chart"),
	QUERY_CHART("query-chart", "query-chart");
	
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