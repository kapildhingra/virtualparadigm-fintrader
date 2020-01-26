package com.virtualparadigm.fintrader.app.chart.rest.impl.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

@SpringBootApplication(scanBasePackages="com.virtualparadigm.fintrader.app.reference.service")
public class ChartRestApplication
{
	private static final VParadigmLogger logger = new VParadigmLogger(ChartRestApplication.class);
	
	public static void main(String[] args)
	{
		logger.debug("MY DEBUG STATEMENT!!!!!!!!!!!!!!!");
		logger.info("MY INFO STATEMENT<<<<<<<<<<<<<<<<");
		SpringApplication.run(ChartRestApplication.class, args);
	}
}