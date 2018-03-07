package com.stampyt.hello.controller.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

public class DateTimeWithZSerializer extends StdSerializer<DateTime> {

    private static DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public DateTimeWithZSerializer() {
        this(null);
    }

    public DateTimeWithZSerializer(Class<DateTime> t) {
        super(t);
    }

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(dateTimeFormatter.print(value));
    }
}
