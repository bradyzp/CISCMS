package net.nsnsns.ciscms.protocols;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class TimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        if (source.isBlank()) {
            return LocalTime.NOON;
        }
        String[] parts = source.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return LocalTime.of(hour, minute);
    }
}
