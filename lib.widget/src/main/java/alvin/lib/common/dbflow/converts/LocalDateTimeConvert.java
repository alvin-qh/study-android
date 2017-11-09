package alvin.lib.common.dbflow.converts;


import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import alvin.lib.common.time.Times;

public class LocalDateTimeConvert extends TypeConverter<Long, LocalDateTime> {

    @Override
    public Long getDBValue(LocalDateTime model) {
        if (model == null) {
            return null;
        }
        return Times.toDate(model, ZoneOffset.UTC).getTime();
    }

    @Override
    public LocalDateTime getModelValue(Long data) {
        if (data == null) {
            return null;
        }

        return Times.toLocalDateTime(new Date(data), ZoneOffset.UTC);
    }
}
