package alvin.base.preferences.domain.models;

import java.time.LocalDate;

public class Person {
    private final String name;
    private final Gender gender;
    private final LocalDate birthday;

    public Person(String name, Gender gender, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
