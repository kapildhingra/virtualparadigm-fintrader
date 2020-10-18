package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vparadigm.shared.finance.ts.SimpleUserSpace;

public class ChartUserSpaceMapper
{
	public static SimpleUserSpace toChartUserSpace(String userSpace)
	{
		SimpleUserSpace chartUserSpace = null;
		if(StringUtils.isNotEmpty(userSpace))
		{
			chartUserSpace = new SimpleUserSpace(userSpace);
		}
		return chartUserSpace;
	}
	
	public static Collection<SimpleUserSpace> toChartUserSpaces(Collection<String> userSpaces)
	{
		List<SimpleUserSpace> chartUserSpaceList = null;
		if(userSpaces != null)
		{
			chartUserSpaceList = new ArrayList<SimpleUserSpace>();
			for(String userSpace : userSpaces)
			{
				chartUserSpaceList.add(ChartUserSpaceMapper.toChartUserSpace(userSpace));
			}
		}
		return chartUserSpaceList;
	}
}