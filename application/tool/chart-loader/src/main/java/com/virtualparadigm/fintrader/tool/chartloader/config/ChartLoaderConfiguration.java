package com.virtualparadigm.fintrader.tool.chartloader.config;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration2.CompositeConfiguration;

import com.vparadigm.shared.comp.common.config.CascadingConfigurationFactory;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

public class ChartLoaderConfiguration
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(ChartLoaderConfiguration.class);
	private static final String INSTRUMENT_LOADER_CONFIG_FILE = "chart-loader.properties";
	
	public static final String INSTRUMENT_LOADER_GLOBAL_KEY_PREFIX = "chartloader";
	
	public static final String KEY_INFLUX = INSTRUMENT_LOADER_GLOBAL_KEY_PREFIX + ".influx";
	public static final String KEY_INFLUX_HOST = KEY_INFLUX + ".host";
	public static final String KEY_INFLUX_PORT = KEY_INFLUX + ".port";
	public static final String KEY_INFLUX_USER = KEY_INFLUX + ".user";
	public static final String KEY_INFLUX_PASS = KEY_INFLUX + ".pass";
	public static final String KEY_INFLUX_DB = KEY_INFLUX + ".db";
	public static final String KEY_INFLUX_RETENTION = KEY_INFLUX + ".retention";
	
	public static final String KEY_ALPHA_VANTAGE = INSTRUMENT_LOADER_GLOBAL_KEY_PREFIX + ".alphavantage";
	public static final String KEY_ALPHA_VANTAGE_API = KEY_ALPHA_VANTAGE + ".api";
	public static final String KEY_ALPHA_VANTAGE_API_KEY = KEY_ALPHA_VANTAGE_API + ".key";
	public static final String KEY_ALPHA_VANTAGE_API_TIMEOUT = KEY_ALPHA_VANTAGE_API + ".timeout.ms";
	
	public static final String KEY_IEX = INSTRUMENT_LOADER_GLOBAL_KEY_PREFIX + ".iex";
	public static final String KEY_IEX_API = KEY_IEX + ".api";
	public static final String KEY_IEX_SERVICE = KEY_IEX_API + ".service";
	public static final String KEY_IEX_TOKEN = KEY_IEX_API + ".token";
	public static final String KEY_IEX_TOKEN_PUBLISHABLE = KEY_IEX_TOKEN + ".publishable";
	public static final String KEY_IEX_TOKEN_SECRET = KEY_IEX_TOKEN + ".secret";
	
	
	private static CompositeConfiguration config;
	
	static
	{
		ChartLoaderConfiguration.config = CascadingConfigurationFactory.createCascadingConfiguration(new String[]{INSTRUMENT_LOADER_CONFIG_FILE});
		ChartLoaderConfiguration.logProperties(ChartLoaderConfiguration.asProperties());
		
	}
	
	public static Properties asProperties()
	{
		Properties properties = new Properties();
		String key;
		for(Iterator<String> it = ChartLoaderConfiguration.config.getKeys(); it.hasNext();)
		{
			key = it.next();
			if(key.startsWith(ChartLoaderConfiguration.INSTRUMENT_LOADER_GLOBAL_KEY_PREFIX))
			{
				properties.setProperty(key, ChartLoaderConfiguration.config.getString(key));
			}
		}
		return properties;
	}
	
	
	
	// =======================================================
	// CONFIG METHODS
	// =======================================================
	public static String getInfluxHost()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_INFLUX_HOST, null);
	}
	public static String getInfluxPort()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_INFLUX_PORT, null);
	}
	public static String getInfluxUser()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_INFLUX_USER, null);
	}
	public static String getInfluxPass()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_INFLUX_PASS, null);
	}
	public static String getInfluxDatabase()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_INFLUX_DB, null);
	}
	public static String getInfluxRetention()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_INFLUX_RETENTION, null);
	}
	public static String getAlphaVantageAPIKey()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_ALPHA_VANTAGE_API_KEY, null);
	}
	public static Integer getAlphaVantageAPITimeoutMillis()
	{
		return ChartLoaderConfiguration.config.getInteger(ChartLoaderConfiguration.KEY_ALPHA_VANTAGE_API_TIMEOUT, 3000);
	}
	public static String getIEXAPIPublishableToken()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_IEX_TOKEN_PUBLISHABLE, null);
	}
	public static String getIEXAPISecretToken()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_IEX_TOKEN_SECRET, null);
	}
	public static String getIEXAPIService()
	{
		return ChartLoaderConfiguration.config.getString(ChartLoaderConfiguration.KEY_IEX_SERVICE, null);
	}
	
	// =======================================================
	// UTILITY METHODS
	// =======================================================
	private static void logProperties(Properties props)
	{
		if(props != null)
		{
			LOGGER.debug("loading instrument loader configuration with the following values:");
			for(Entry<Object, Object> entry : props.entrySet())
			{
				LOGGER.debug(entry.getKey() + ":" + entry.getValue());
			}
		}
	}
}