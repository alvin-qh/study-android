package alvin.lib.common.dbflow.converts;


import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import alvin.lib.common.time.Times;

public class LocalDateConvert extends TypeConverter<Long, LocalDate> {

    @Override
    public Long getDBValue(LocalDate model) {
        if (model == null) {
            return null;
        }
        return Times.toDate(model, ZoneOffset.UTC).getTime();
    }

    @Override
    public LocalDate getModelValue(Long data) {
        if (data == null) {
            return null;
        }

        return Times.toLocalDate(new Date(data), ZoneOffset.UTC);
    }
}
