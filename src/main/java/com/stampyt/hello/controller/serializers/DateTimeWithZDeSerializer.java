package com.stampyt.hello.controller.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

public class DateTimeWithZDeSerializer extends StdDeserializer<DateTime> {
    private static DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public DateTimeWithZDeSerializer() {
        this(null);
    }

    public DateTimeWithZDeSerializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateTime = p.getText();
        DateTime deserializedDate = null;
        return dateTimeFormatter.parseDateTime(dateTime).withZone(DateTimeZone.UTC);
    }
}
