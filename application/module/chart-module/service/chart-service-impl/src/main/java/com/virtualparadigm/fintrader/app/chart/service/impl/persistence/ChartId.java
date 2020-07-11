package com.virtualparadigm.fintrader.app.chart.service.impl.persistence;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ChartId implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String DELIMITER = "_";
	
//	private String identifier;
//	private String userSpace;
	private SampleRecordFrequency sampleRecordFrequency;
	private String symbol;
	private String chartName;
	
//	public ChartId(String userSpace, SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName)
	public ChartId(SampleRecordFrequency sampleRecordFrequency, String symbol, String chartName)
	{
//		this.userSpace = userSpace;
		this.symbol = symbol;
		this.sampleRecordFrequency = sampleRecordFrequency;
		this.chartName = chartName;
	}

//	public String getUserSpace()
//	{
//		return userSpace;
//	}

	public SampleRecordFrequency getSampleRecordFrequency()
	{
		return sampleRecordFrequency;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public String getChartName()
	{
		return chartName;
	}

	public String asString()
	{
		StringBuffer strBuf = new StringBuffer();
//		if(StringUtils.isNotEmpty(userSpace))
//		{
//			strBuf.append(userSpace);
//		}
//		else
//		{
//			strBuf.append(ChartRecord.DEFAULT_USERSPACE);
//		}
//		strBuf.append(ChartId.DELIMITER);
		
		if(sampleRecordFrequency != null)
		{
			strBuf.append(sampleRecordFrequency.name());
		}
		else
		{
			strBuf.append(ChartRecord.DEFAULT_SAMPLE_FREQUENCY.name());
		}
		strBuf.append(ChartId.DELIMITER);
		
		if(StringUtils.isNotEmpty(symbol))
		{
			strBuf.append(symbol);
		}
		else
		{
			strBuf.append(ChartRecord.DEFAULT_SYMBOL);
		}
		strBuf.append(ChartId.DELIMITER);


		if(StringUtils.isNotEmpty(chartName))
		{
			strBuf.append(chartName);
		}
		else
		{
			strBuf.append(ChartRecord.DEFAULT_CHART_NAME);
		}
		return strBuf.toString();		
//		return this.identifier;
	}
	
	//  ================================================================
	//  UTILITY METHODS
	//  ================================================================
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder(17,31);
	    builder.append(this.asString());
	    return builder.toHashCode();		
	}
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (obj == this)
		{
			return true;
		}
		if (obj.getClass() != getClass())
		{
			return false;
		}
		ChartId that = (ChartId)obj;
		EqualsBuilder builder = new EqualsBuilder();
	    builder.append(this.asString(), that.asString());
		return builder.isEquals();
	}
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}	
}