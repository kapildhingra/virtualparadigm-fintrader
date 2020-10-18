package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleRecord;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVectorRecord;
import com.vparadigm.shared.finance.ts.Chart;

public class SampleVectorRecordMapper
{
	public static List<SampleVectorRecord> toSampleVectorRecords(Chart chart, boolean includeNulls)
	{
		List<SampleVectorRecord> sampleVectorRecordList = null;
		if(chart != null)
		{
			Map<String, String> unitOfMeasureMapping = chart.getUnitOfMeasureMapping(); 
			sampleVectorRecordList = new ArrayList<SampleVectorRecord>();
			Set<String> dtsNameSet = chart.getIndexedDTSNames();
			
			SampleRecord[] sampleRecords = null;
			Map<String, BigDecimal> dtsValueMap = null;
			int sampleRecordsIndex = 0;
System.out.println(">>>>>>>>>> chart time series size: " + chart.getTimeSeriesSize());
			for(int dtsVectorIndex=0; dtsVectorIndex<chart.getTimeSeriesSize(); dtsVectorIndex++)
			{
				//Chart object has SOME value for every item in the matrix
				sampleRecords = new SampleRecord[dtsNameSet.size()];
				dtsValueMap = chart.atForwardIndexMap(dtsVectorIndex, includeNulls);

System.out.println(">>>>>>>>>>   dtsVectorIndex: " + dtsVectorIndex + " dtsValueMap size: " + dtsValueMap.size());
				
				for(String dtsName : dtsValueMap.keySet())
				{
System.out.println(">>>>>>>>>>     sampleRecordsIndex: " + sampleRecordsIndex + " value:" + dtsValueMap.get(dtsName));
					sampleRecords[sampleRecordsIndex] = new SampleRecord(dtsName, dtsValueMap.get(dtsName), unitOfMeasureMapping.get(dtsName));
					sampleRecordsIndex++;
				}
				if(sampleRecordsIndex > 0)
				{
System.out.println(">>>>>>>>>>       adding sample vector record");
					sampleVectorRecordList.add(new SampleVectorRecord(chart.getForwardIndexTime(dtsVectorIndex).getMillis(), sampleRecords));
				}
				sampleRecords = null;
				sampleRecordsIndex = 0;
			}
		}
		return sampleVectorRecordList;
	}
}