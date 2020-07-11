package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.util.ArrayList;
import java.util.List;

import com.virtualparadigm.fintrader.tool.chartloader.delegate.InstrumentVO;

public class InstrumentDataMapper
{
	public static InstrumentData toInstrumentData(InstrumentVO instrumentVO)
	{
		InstrumentData instrumentData = null;
		if(instrumentVO != null)
		{
			instrumentData = new InstrumentData(instrumentVO.getExchange(), instrumentVO.getSymbol());
		}
		return instrumentData;
	}
	
	public static List<InstrumentData> toInstrumentDatas(List<InstrumentVO> instrumentVOList)
	{
		List<InstrumentData> instrumentDataList = null;
		if(instrumentVOList != null)
		{
			instrumentDataList = new ArrayList<InstrumentData>();
			for(InstrumentVO instrumentVO : instrumentVOList)
			{
				instrumentDataList.add(InstrumentDataMapper.toInstrumentData(instrumentVO));
			}
		}
		return instrumentDataList;
	}
	
}