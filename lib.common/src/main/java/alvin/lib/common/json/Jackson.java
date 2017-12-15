package alvin.lib.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Strings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Jackson {

    private Jackson() { }

    public static ObjectMapper create() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(final LocalDate value,
                                  final JsonGenerator gen,
                                  final SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(value.format(DateTimeFormatter.ISO_DATE));
                }
            }
        });
        module.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(final JsonParser p,
                                         final DeserializationContext ctx) throws IOException {
                final String value = p.getValueAsString();
                if (Strings.isNullOrEmpty(value)) {
                    return null;
                }
                return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(value));
            }
        });
        mapper.registerModule(module);

        return mapper;
    }
}
