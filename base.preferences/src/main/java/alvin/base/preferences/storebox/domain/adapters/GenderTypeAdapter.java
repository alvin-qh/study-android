package alvin.adv.preferences.storebox.domain.adapters;

import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

import alvin.adv.preferences.common.domain.models.Gender;

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
