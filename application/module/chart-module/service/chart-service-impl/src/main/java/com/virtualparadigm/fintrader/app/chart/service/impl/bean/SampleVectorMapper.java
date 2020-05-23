package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleData;
import com.virtualparadigm.fintrader.app.chart.service.impl.persistence.SampleVectorData;
import com.vparadigm.shared.finance.chart.Chart;

public class SampleVectorMapper
{
	public static List<SampleVectorData> toSampleVectorDatas(Chart chart)
	{
		List<SampleVectorData> sampleVectorDataList = null;
		if(chart != null)
		{
			Map<String, String> unitOfMeasureMapping = chart.getUnitOfMeasureMapping(); 
			sampleVectorDataList = new ArrayList<SampleVectorData>();
			Set<String> dtsNameSet = chart.getIndexedDTSNames();
			
			SampleData[] sampleDatas = null;
			Map<String, BigDecimal> dtsValueMap = null;
			int sampleDatasIndex = 0;
			for(int dtsVectorIndex=0; dtsVectorIndex<chart.getTimeSeriesSize(); dtsVectorIndex++)
			{
				//Chart object has SOME value for every item in the matrix
				sampleDatas = new SampleData[dtsNameSet.size()];
				dtsValueMap = chart.atForwardIndexMap(dtsVectorIndex);
				
				for(String dtsName : dtsValueMap.keySet())
				{
					sampleDatas[sampleDatasIndex] = new SampleData(dtsName, dtsValueMap.get(dtsName), unitOfMeasureMapping.get(dtsName));
					sampleDatasIndex++;
				}
				sampleVectorDataList.add(new SampleVectorData(chart.getForwardIndexTime(dtsVectorIndex).getMillis(), sampleDatas));
				sampleDatas = null;
				sampleDatasIndex = 0;
			}
		}
		return sampleVectorDataList;
	}
}