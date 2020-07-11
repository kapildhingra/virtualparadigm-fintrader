package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.virtualparadigm.fintrader.app.chart.service.api.SampleDTOFrequency;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;
import com.vparadigm.shared.comp.common.validate.VParadigmValidator;

public class ChartDelegateCSVFileImpl implements ChartDelegate
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(ChartDelegateCSVFileImpl.class);
	private static final String FIELD_DATETIME = "DATETIME";
	private static final String FIELD_OPEN = "OPEN";
	private static final String FIELD_HIGH = "HIGH";
	private static final String FIELD_LOW = "LOW";
	private static final String FIELD_CLOSE = "CLOSE";
	private static final String FIELD_VOLUME = "VOLUME";

	private String baseDirectory;
	//private String fileNameRegex;
	private String normalizedFileNameRegex;
	private String fileNameRegexSymbolSegment;
	//private Pattern fileNamePattern;
	private Pattern csvLinePattern;
	private Map<String, Integer> fieldGroupMap;
    private DateTimeFormatter dateTimeFormatter; 
	
	public ChartDelegateCSVFileImpl(String strBaseDirectory)
	{
	}
	public ChartDelegateCSVFileImpl(String baseDirectory, String fileNameRegex, String fileNameRegexSymbolSegment, String csvLineRegex, List<String> csvLineFields, String localDateTimeFormat)
	{
		this.baseDirectory = baseDirectory;
		//this.fileNameRegex = fileNameRegex;
		this.normalizedFileNameRegex = fileNameRegex.replace("\\", "");
		this.fileNameRegexSymbolSegment = fileNameRegexSymbolSegment;
		//this.fileNamePattern = Pattern.compile(fileNameRegex);
		this.csvLinePattern = Pattern.compile(csvLineRegex);
		this.fieldGroupMap = new HashMap<String,Integer>();
		if(csvLineFields != null && csvLineFields.size() > 0)
		{
			for(int i=0; i<csvLineFields.size(); i++)
			{
				this.fieldGroupMap.put(csvLineFields.get(i), i+1);
			}
		}
		else
		{
			throw new IllegalStateException("csvLineFields is null or empty");
		}
		
		this.dateTimeFormatter = 
				new DateTimeFormatterBuilder().appendPattern(localDateTimeFormat)
					.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
					.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
					.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
					.toFormatter(); 
	}
	

	@Override
	public List<ChartVectorVO> getChartVectors(String symbol, SampleDTOFrequency sampleFrequency, LocalDateTime startTime, LocalDateTime endTime)
	{
		List<ChartVectorVO> instrumentDataList = null;
		File symbolFile = ChartDelegateCSVFileImpl.getSymbolFile(this.baseDirectory, symbol, sampleFrequency, this.normalizedFileNameRegex, this.fileNameRegexSymbolSegment);
		if(symbolFile != null)
		{
			LineIterator it = null;
			try
			{
				it = FileUtils.lineIterator(symbolFile, "UTF-8");
				String line = null;
				Matcher matcher = null;
				instrumentDataList = new ArrayList<ChartVectorVO>();
				while(it.hasNext())
				{
					line = it.nextLine();
					matcher = this.csvLinePattern.matcher(line);
					LocalDateTime localDateTime = null;
					if(matcher.matches())
					{
						localDateTime = LocalDateTime.parse(matcher.group(fieldGroupMap.get(FIELD_DATETIME)), dateTimeFormatter);
						if(localDateTime != null)
						{
							if((startTime == null || localDateTime.compareTo(startTime) >= 0) && (endTime == null || localDateTime.compareTo(endTime) <= 0))
							{
								instrumentDataList.add(
										new ChartVectorVO(
												localDateTime, 
												new BigDecimal(matcher.group(fieldGroupMap.get(FIELD_OPEN))), 
												new BigDecimal(matcher.group(fieldGroupMap.get(FIELD_HIGH))), 
												new BigDecimal(matcher.group(fieldGroupMap.get(FIELD_LOW))), 
												new BigDecimal(matcher.group(fieldGroupMap.get(FIELD_CLOSE))), 
												new BigDecimal(matcher.group(fieldGroupMap.get(FIELD_VOLUME)))));
							}
						}
						else
						{
							LOGGER.debug("no datetime found for sample");
						}
					}
				}
			}
			catch(Throwable t)
			{
				LOGGER.error("error getting instrument data from file", t);
			}
			finally
			{
				if(it != null)
				{
					LineIterator.closeQuietly(it);
				}
			}
		}
		else
		{
			LOGGER.warning("symbolFile resolved to null");
		}
		return instrumentDataList;
	}
	
	private static File getSymbolFile(String baseDirectory, String symbol, SampleDTOFrequency sampleFrequency, String normalizedFileNameRegex, String fileNameRegexSymbolSegment)
	{
		VParadigmValidator.validateNotEmpty("baseDirectory", baseDirectory);
		VParadigmValidator.validateNotEmpty("symbol", symbol);
		VParadigmValidator.validateNotNull("sampleFrequency", sampleFrequency);
		String strSymbolFile = null;

		switch(sampleFrequency)
		{
			case DAY:
			{
				strSymbolFile = normalizedFileNameRegex.replace(fileNameRegexSymbolSegment, symbol);
				break;
			}
			default:
			{
			}
		}
		return new File(baseDirectory + "/" + strSymbolFile);
	}
	
	public static void main(String[] args)
	{
		
		
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		DateTimeFormatter dateTimeFormatter = 
				new DateTimeFormatterBuilder().appendPattern("yyyyMMdd['T'[HH][:mm][:ss][.SSS]]")
					.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
					.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
					.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
					.toFormatter(); 
		
		
		LocalDateTime localDateTime = LocalDateTime.parse("19980102", dateTimeFormatter);
		System.out.println("done");
	}

}