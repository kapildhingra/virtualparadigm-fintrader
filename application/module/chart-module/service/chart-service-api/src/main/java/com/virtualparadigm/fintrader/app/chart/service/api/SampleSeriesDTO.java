package com.virtualparadigm.fintrader.app.chart.service.api;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SampleSeriesDTO 
{
	private static final long serialVersionUID = 1L;
	private String name;
	private SampleDTO[] samples;
	
	public SampleSeriesDTO()
	{
		super();
	}
	public SampleSeriesDTO(String name, SampleDTO[] samples)
	{
		super();
		this.name = name;
		this.samples = samples;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public SampleDTO[] getSamples()
	{
		return samples;
	}
	public void setSamples(SampleDTO[] samples)
	{
		this.samples = samples;
	}

	//  ================================================================
	//  UTILITY METHODS
	//  ================================================================
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder(17,31);
	    builder.append(this.getName());
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
		SampleSeriesDTO that = (SampleSeriesDTO)obj;
		EqualsBuilder builder = new EqualsBuilder();
	    builder.append(this.getName(), that.getName());
		return builder.isEquals();
	}
	
}