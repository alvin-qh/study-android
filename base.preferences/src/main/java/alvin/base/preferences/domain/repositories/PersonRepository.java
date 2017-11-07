package alvin.base.preferences.domain.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDate;

import alvin.base.preferences.domain.models.Gender;
import alvin.base.preferences.domain.models.Person;

public class PersonRepository {

    private final Context context;

    public PersonRepository(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences("original", Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getPreferencesEditor() {
        return getPreferences().edit();
    }

    public Person load() {
        SharedPreferences p = getPreferences();

        String name = p.getString("name", "");
        Gender gender = Gender.valueOf(p.getString("gender", "MALE"));
        int year = p.getInt("birthdayYear", 0);
        int month = p.getInt("birthdayMonth", 0);
        int date = p.getInt("birthdayDayOfMonth", 0);

        if (year == 0 || month == 0 || date == 0) {
            return new Person(name, gender, null);
        }
        return new Person(name, gender, LocalDate.of(year, month, date));
    }

    public void save(Person person) {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putString("name", person.getName());
        editor.putString("gender", person.getGender().toString());

        LocalDate birthday = person.getBirthday();
        editor.putInt("birthdayYear", birthday.getYear());
        editor.putInt("birthdayMonth", birthday.getMonthValue());
        editor.putInt("birthdayDayOfMonth", birthday.getDayOfMonth());

        editor.apply();
    }
}
