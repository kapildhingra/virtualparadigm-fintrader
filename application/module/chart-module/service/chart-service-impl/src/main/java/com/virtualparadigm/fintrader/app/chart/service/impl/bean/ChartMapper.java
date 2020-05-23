package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.ChartData;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleData;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVectorData;
import com.vparadigm.shared.finance.chart.Chart;
import com.vparadigm.shared.finance.chart.ChartUserSpace;
import com.vparadigm.shared.ts.IDiscreteTimeSeries;
import com.vparadigm.shared.ts.SampleFrequency;

public class ChartMapper
{
	//private static final Instant EPOCH_INSTANT = Instant.f
	
	public static Chart toChart(ChartData chartData, List<SampleVectorData> sampleVectorList)
	{
		Chart chart = null;
		if(chartData != null)
		{
			if(sampleVectorList != null && sampleVectorList.size() > 0)
			{
				SampleVectorData firstSampleVectorData = sampleVectorList.get(0);
				SampleVectorData lastSampleVectorData = sampleVectorList.get(sampleVectorList.size()-1);
				
				ChartUserSpace chartUserSpace = new ChartUserSpace(chartData.getUserspace());
				
				chart = 
						chartUserSpace.createChart(
								SampleFrequency.valueOf(
										chartData.getSampleFrequencyData().name()), 
								chartData.getSymbol(), 
								chartData.getChartName(), 
								new Interval(firstSampleVectorData.getGmtTimestamp(), lastSampleVectorData.getGmtTimestamp()));
				
				SampleVectorData sampleVectorData = null;
				SampleData[] sampleDatas = null;
				SampleData sampleData = null;
				IDiscreteTimeSeries<BigDecimal> registeredDTS = null;
				
				for(int sampleVectorIndex=0; sampleVectorIndex<sampleVectorList.size(); sampleVectorIndex++)
				{
					sampleVectorData = sampleVectorList.get(sampleVectorIndex);
					if(sampleVectorData != null)
					{
						sampleDatas = sampleVectorData.getSampleData();
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
	
}