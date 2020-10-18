package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartRecord;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleRecord;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVectorRecord;
import com.vparadigm.shared.finance.ts.Chart;
import com.vparadigm.shared.finance.ts.IChartSpace;
import com.vparadigm.shared.finance.ts.IUserSpace;
import com.vparadigm.shared.finance.ts.SimpleUserSpace;
import com.vparadigm.shared.ts.IDiscreteTimeSeries;
import com.vparadigm.shared.ts.SampleFrequency;

public class ChartMapper
{
	//private static final Instant EPOCH_INSTANT = Instant.f
	
	public static Chart toChart(ChartRecord chartRecord, List<SampleVectorRecord> sampleVectorList)
	{
		Chart chart = null;
		if(chartRecord != null)
		{
			IUserSpace userSpace = new SimpleUserSpace(chartRecord.getUserspace());
			IChartSpace chartSpace = userSpace.createChartSpace(SampleFrequency.valueOf(chartRecord.getSampleRecordFrequency().name()));
			if(sampleVectorList == null || sampleVectorList.size() == 0)
			{
				chart = 
						chartSpace.createChart( 
								chartRecord.getSymbol(), 
								chartRecord.getChartName(), 
								new Interval(0, Instant.now().toEpochMilli()));
			}
			else
			{
				SampleVectorRecord firstSampleVectorData = sampleVectorList.get(0);
				SampleVectorRecord lastSampleVectorData = sampleVectorList.get(sampleVectorList.size()-1);
				
				chart = 
						chartSpace.createChart(
								chartRecord.getSymbol(), 
								chartRecord.getChartName(), 
								new Interval(firstSampleVectorData.getGmtTimestamp(), lastSampleVectorData.getGmtTimestamp()));
				
				SampleVectorRecord sampleVectorData = null;
				SampleRecord[] sampleDatas = null;
				SampleRecord sampleData = null;
				IDiscreteTimeSeries<BigDecimal> registeredDTS = null;
				
				for(int sampleVectorIndex=0; sampleVectorIndex<sampleVectorList.size(); sampleVectorIndex++)
				{
					sampleVectorData = sampleVectorList.get(sampleVectorIndex);
					if(sampleVectorData != null)
					{
						sampleDatas = sampleVectorData.getSampleRecords();
						if(sampleDatas != null)
						{
							for(int sampleDataIndex=0; sampleDataIndex<sampleDatas.length; sampleDataIndex++)
							{
								sampleData = sampleDatas[sampleDataIndex];
								registeredDTS = chart.getRegisteredDiscreteTimeSeries(sampleData.getField());
								if(registeredDTS != null)
								{
									registeredDTS.addMeasurement(new DateTime(sampleVectorData.getGmtTimestamp()), sampleData.getValue());
								}
								else
								{
									registeredDTS = chart.createRegisteredDiscreteTimeSeries(sampleData.getField(), sampleData.getUnitOfMeasure(), null);
									registeredDTS.addMeasurement(new DateTime(sampleVectorData.getGmtTimestamp()), sampleData.getValue());
								}
							}
						}
					}
				}
			}
		}
		return chart;
	}
	
	
	public static Collection<Chart> toCharts(Collection<ChartRecord> chartRecords)
	{
		List<Chart> chartList = null;
		if(chartRecords != null)
		{
			chartList = new ArrayList<Chart>();
			for(ChartRecord chartRecord : chartRecords)
			{
				chartList.add(ChartMapper.toChart(chartRecord, null));
			}
		}
		return chartList;
	}

	
}