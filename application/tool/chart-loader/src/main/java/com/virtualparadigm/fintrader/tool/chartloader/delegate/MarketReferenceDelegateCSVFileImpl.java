package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;

import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

public class MarketReferenceDelegateCSVFileImpl implements MarketReferenceDelegate
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(MarketReferenceDelegateCSVFileImpl.class);
	
	private static final String MIC_ZONE_KEY_MIC = "MIC";
	private static final String MIC_ZONE_KEY_TIMEZONE = "TIMEZONE";
	
	private Map<String, MarketVO> marketMap;
	
	public MarketReferenceDelegateCSVFileImpl(String micZoneMappingFilePath)
	{
		try
		{
			this.marketMap = MarketReferenceDelegateCSVFileImpl.getMarketMap(micZoneMappingFilePath);
		}
		catch(FileNotFoundException fnfe)
		{
			LOGGER.error("error loading micZoneMappingFile: " + micZoneMappingFilePath, fnfe);
		}
		catch(IOException ioe)
		{
			LOGGER.error("error loading micZoneMappingFile: " + micZoneMappingFilePath, ioe);
		}
	}
	
	private static Map<String, MarketVO> getMarketMap(String micZoneMappingFilePath) throws FileNotFoundException, IOException
	{
		Map<String, MarketVO> marketMap = null;
		if(StringUtils.isNotEmpty(micZoneMappingFilePath))
		{
			if(micZoneMappingFilePath.endsWith(".csv"))
			{
				Reader reader = new FileReader(micZoneMappingFilePath);
				Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(MIC_ZONE_KEY_MIC, MIC_ZONE_KEY_TIMEZONE).parse(reader);
				String mic = null;
				String timezone = null;
				if(records != null)
				{
					marketMap = new HashMap<String, MarketVO>();
					for(CSVRecord record : records)
					{
						mic = record.get(MIC_ZONE_KEY_MIC);
						timezone = record.get(MIC_ZONE_KEY_TIMEZONE);
						if(StringUtils.isNotEmpty(mic) && StringUtils.isNotEmpty(timezone))
						{
							marketMap.put(mic, new MarketVO(mic, DateTimeZone.forID(timezone)));
						}
					}
				}
			}				
		}
		return marketMap;
	}
	
	
	@Override
	public List<MarketVO> getMarkets()
	{
		return new ArrayList<MarketVO>(this.marketMap.values());
	}

	@Override
	public List<MarketVO> getMarketsByCountry(String country)
	{
		throw new NotImplementedException("MarketReferenceDelegateCSVImpl::getMarketsByCountry() not implemented");
	}
	
	
	public static void printMarkets(List<MarketVO> marketList)
	{
		if(marketList != null)
		{
			for(MarketVO marketVO : marketList)
			{
				System.out.println("mic: " + marketVO.getMarketIdentifierCode() + " timezone: " + marketVO.getDateTimeZone());
			}
		}
	}
	
	public static void main(String[] args)
	{
		MarketReferenceDelegate marketReferenceDelegate = 
				new MarketReferenceDelegateCSVFileImpl("C:/dev/workbench/workspaces/virtualparadigm_workspace/virtualparadigm-fintrader/application/tool/chart-loader/src/main/config/mic-zones.csv");
		MarketReferenceDelegateCSVFileImpl.printMarkets(marketReferenceDelegate.getMarkets());
	}
}