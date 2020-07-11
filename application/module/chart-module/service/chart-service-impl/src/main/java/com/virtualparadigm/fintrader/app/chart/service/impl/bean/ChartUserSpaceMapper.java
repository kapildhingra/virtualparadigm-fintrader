package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vparadigm.shared.finance.chart.ChartUserSpace;

public class ChartUserSpaceMapper
{
	public static ChartUserSpace toChartUserSpace(String userSpace)
	{
		ChartUserSpace chartUserSpace = null;
		if(StringUtils.isNotEmpty(userSpace))
		{
			chartUserSpace = new ChartUserSpace(userSpace);
		}
		return chartUserSpace;
	}
	
	public static Collection<ChartUserSpace> toChartUserSpaces(Collection<String> userSpaces)
	{
		List<ChartUserSpace> chartUserSpaceList = null;
		if(userSpaces != null)
		{
			chartUserSpaceList = new ArrayList<ChartUserSpace>();
			for(String userSpace : userSpaces)
			{
				chartUserSpaceList.add(ChartUserSpaceMapper.toChartUserSpace(userSpace));
			}
		}
		return chartUserSpaceList;
	}
}