package com.virtualparadigm.fintrader.tool.chartloader.util;


import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime>
{
	private static final long serialVersionUID = 1L;

	protected LocalDateTimeSerializer()
	{
		super(LocalDateTime.class);
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException
	{
		generator.writeString(value.format(FormatUtil.DATE_TIME_FORMATTER));
	}
}