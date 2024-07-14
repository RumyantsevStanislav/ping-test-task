package ru.test.ping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static ru.test.ping.utils.Consts.MOSCOW_TIME_ZONE;

@Mapper
public interface TimestampMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param offsetDateTime
     * @return
     */
    @Named("mapOffsetDateTime")
    static String mapOffsetDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.atZoneSameInstant(ZoneId.of(MOSCOW_TIME_ZONE)).toLocalDateTime().format(formatter);
    }
}