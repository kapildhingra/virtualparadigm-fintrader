package com.virtualparadigm.fintrader.app.chart.service.api;

import java.util.Collection;

import com.vparadigm.shared.comp.common.identity.GUID;

public interface ChartService
{
	Collection<ChartDTO> getCharts();
	ChartDTO getChart(GUID chartGUID);
}