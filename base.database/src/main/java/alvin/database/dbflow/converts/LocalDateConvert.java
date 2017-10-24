package alvin.database.dbflow.converts;


import com.google.common.base.Strings;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConvert extends TypeConverter<String, LocalDate> {

    @Override
    public String getDBValue(LocalDate model) {
        if (model == null) {
            return null;
        }
        return model.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public LocalDate getModelValue(String data) {
        if (Strings.isNullOrEmpty(data)) {
            return null;
        }

        return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(data));
    }
}
