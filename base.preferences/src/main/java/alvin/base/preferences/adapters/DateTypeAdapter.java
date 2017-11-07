package alvin.base.preferences.adapters;

import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTypeAdapter extends BaseStringTypeAdapter<LocalDate> {

    @Nullable
    @Override
    public String adaptForPreferences(@Nullable LocalDate value) {
        if (value == null) {
            return "";
        }
        return value.format(DateTimeFormatter.ISO_DATE);
    }

    @Nullable
    @Override
    public LocalDate adaptFromPreferences(@Nullable String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(value));
    }
}
