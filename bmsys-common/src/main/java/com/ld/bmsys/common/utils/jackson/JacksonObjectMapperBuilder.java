package com.ld.bmsys.common.utils.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.function.Function;
import java.util.function.Supplier;

public class JacksonObjectMapperBuilder implements Supplier<ObjectMapper> {

    public static final Function<ZoneId, ZoneId> WITH_SAME_ZONE = zoneId -> zoneId;
    public static final Function<ZoneId, ZoneId> WITH_UTC_ZONE = zoneId -> ZoneOffset.UTC;

    private final Function<ZoneId, ZoneId> withZone;

    public JacksonObjectMapperBuilder() {
        this(WITH_UTC_ZONE);
    }

    public JacksonObjectMapperBuilder(ZoneId zone) {
        this(z -> zone);
    }

    public JacksonObjectMapperBuilder(Function<ZoneId, ZoneId> withZone) {
        this.withZone = withZone;
    }

    protected void registerModule(ObjectMapper mapper) {
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new AfterburnerModule());
        mapper.registerModule(new JavaTimeModule().addSerializer(ZonedDateTime.class, new ZoneIdZonedDateTimeSerializer(withZone)));
    }

    @Override
    public ObjectMapper get() {
        ObjectMapper mapper = new ObjectMapper();
        registerModule(mapper);

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        mapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
        mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        mapper.enable(JsonParser.Feature.ALLOW_YAML_COMMENTS);
        return mapper;
    }
}
