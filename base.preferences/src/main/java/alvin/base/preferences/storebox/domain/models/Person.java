package alvin.base.preferences.storebox.domain.models;

import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.annotations.method.TypeAdapter;

import java.time.LocalDate;

import alvin.base.preferences.common.domain.models.Gender;
import alvin.base.preferences.storebox.domain.adapters.DateTypeAdapter;
import alvin.base.preferences.storebox.domain.adapters.GenderTypeAdapter;

public interface Person {
    @KeyByString("name")
    String getName();

    @KeyByString("name")
    void setName(String name);

    @KeyByString("gender")
    @TypeAdapter(GenderTypeAdapter.class)
    Gender getGender();

    @KeyByString("gender")
    @TypeAdapter(GenderTypeAdapter.class)
    void setGender(Gender gender);

    @KeyByString("birthday")
    @TypeAdapter(DateTypeAdapter.class)
    LocalDate getBirthday();

    @KeyByString("birthday")
    @TypeAdapter(DateTypeAdapter.class)
    void setBirthday(LocalDate birthday);
}
