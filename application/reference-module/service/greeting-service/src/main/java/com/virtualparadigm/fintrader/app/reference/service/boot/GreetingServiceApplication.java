package com.virtualparadigm.fintrader.app.reference.service.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

@SpringBootApplication(scanBasePackages="com.virtualparadigm.fintrader.app.reference.service")
public class GreetingServiceApplication
{
	private static final VParadigmLogger logger = new VParadigmLogger(GreetingServiceApplication.class);
	
	public static void main(String[] args)
	{
		logger.debug("MY DEBUG STATEMENT!!!!!!!!!!!!!!!");
		logger.info("MY INFO STATEMENT<<<<<<<<<<<<<<<<");
		SpringApplication.run(GreetingServiceApplication.class, args);
	}
}