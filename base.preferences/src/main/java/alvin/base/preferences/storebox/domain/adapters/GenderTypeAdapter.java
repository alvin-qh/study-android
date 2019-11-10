package alvin.base.preferences.storebox.domain.adapters;


import com.google.common.base.Strings;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

import alvin.base.preferences.common.domain.models.Gender;
import androidx.annotation.Nullable;

public class GenderTypeAdapter extends BaseStringTypeAdapter<Gender> {

    @Nullable
    @Override
    public String adaptForPreferences(@Nullable Gender value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    @Nullable
    @Override
    public Gender adaptFromPreferences(@Nullable String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return Gender.valueOf(value);
    }
}
