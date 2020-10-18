package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.virtualparadigm.fintrader.app.chart.service.api.UserSpaceDTO;
import com.vparadigm.shared.finance.ts.SimpleUserSpace;

public class UserSpaceDTOMapper
{
	public static UserSpaceDTO toUserSpaceDTO(SimpleUserSpace chartUserSpace)
	{
		UserSpaceDTO userSpaceDTO = null;
		if(chartUserSpace != null)
		{
			userSpaceDTO = new UserSpaceDTO(chartUserSpace.getGUID().getStringValue(), chartUserSpace.getName());
		}
		return userSpaceDTO;
	}
	
	public static Collection<UserSpaceDTO> toUserSpaceDTOs(Collection<SimpleUserSpace> chartUserSpaces)
	{
		List<UserSpaceDTO> userSpaceDTOList = null;
		if(chartUserSpaces != null)
		{
			userSpaceDTOList = new ArrayList<UserSpaceDTO>();
			for(SimpleUserSpace chartUserSpace : chartUserSpaces)
			{
				userSpaceDTOList.add(UserSpaceDTOMapper.toUserSpaceDTO(chartUserSpace));
			}
		}
		return userSpaceDTOList;
	}
}