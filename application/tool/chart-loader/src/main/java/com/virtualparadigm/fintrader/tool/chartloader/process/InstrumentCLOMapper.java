package com.virtualparadigm.fintrader.tool.chartloader.process;

import java.util.ArrayList;
import java.util.List;

import com.virtualparadigm.fintrader.tool.chartloader.delegate.InstrumentVO;

public class InstrumentCLOMapper
{
	public static InstrumentCLO toInstrumentCLO(InstrumentVO instrumentVO)
	{
		InstrumentCLO instrumentData = null;
		if(instrumentVO != null)
		{
			instrumentData = new InstrumentCLO(instrumentVO.getExchange(), instrumentVO.getSymbol());
		}
		return instrumentData;
	}
	
	public static List<InstrumentCLO> toInstrumentCLOs(List<InstrumentVO> instrumentVOList)
	{
		List<InstrumentCLO> instrumentDataList = null;
		if(instrumentVOList != null)
		{
			instrumentDataList = new ArrayList<InstrumentCLO>();
			for(InstrumentVO instrumentVO : instrumentVOList)
			{
				instrumentDataList.add(InstrumentCLOMapper.toInstrumentCLO(instrumentVO));
			}
		}
		return instrumentDataList;
	}
	
}