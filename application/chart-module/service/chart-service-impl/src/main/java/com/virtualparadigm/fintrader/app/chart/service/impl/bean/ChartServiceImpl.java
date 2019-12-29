package com.virtualparadigm.fintrader.app.chart.service.impl.bean;

import java.util.Collection;

import com.virtualparadigm.fintrader.app.chart.service.api.ChartDTO;
import com.virtualparadigm.fintrader.app.chart.service.api.ChartService;
import com.vparadigm.shared.comp.common.identity.GUID;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

public class ChartServiceImpl implements ChartService
{
	private static final VParadigmLogger logger = new VParadigmLogger(ChartServiceImpl.class);
	
	@Override
	public Collection<ChartDTO> getCharts()
	{
		logger.info("ChartServiceImpl::getCharts()");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartDTO getChart(GUID chartGUID)
	{
		logger.info("ChartServiceImpl::getChart()");
		// TODO Auto-generated method stub
		return null;
	}
	
}