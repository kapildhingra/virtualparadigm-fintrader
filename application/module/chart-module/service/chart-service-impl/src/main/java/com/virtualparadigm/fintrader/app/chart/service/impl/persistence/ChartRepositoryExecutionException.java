package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

public class ChartRepositoryExecutionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public ChartRepositoryExecutionException(String msg)
	{
		super(msg);
	}
	public ChartRepositoryExecutionException(Throwable t)
	{
		super(t);
	}
	public ChartRepositoryExecutionException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
