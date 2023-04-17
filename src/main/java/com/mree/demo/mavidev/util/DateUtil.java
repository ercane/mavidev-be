package com.mree.demo.mavidev.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateUtil {

    public static LocalDateTime toLocalDateTime(long timestampMilliseconds, TimeZone timeZone) {

        TimeZone tz = timeZone == null ? TimeZone.getDefault() : timeZone;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMilliseconds), tz.toZoneId());
    }

    public static long toTimestampMilliSeconds(LocalDateTime date, TimeZone timeZone) {

        TimeZone tz = timeZone == null ? TimeZone.getDefault() : timeZone;
        return date.atZone(tz.toZoneId()).toInstant().toEpochMilli();
    }
}
