package alvin.lib.common.dbflow.converts;


import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.time.LocalDate;

public class LocalDateConvert extends TypeConverter<String, LocalDate> {

    @Override
    public String getDBValue(LocalDate model) {
        if (model == null) {
            return null;
        }
        return model.toString();
    }

    @Override
    public LocalDate getModelValue(String data) {
        if (data == null) {
            return null;
        }
        return LocalDate.parse(data);
    }
}
