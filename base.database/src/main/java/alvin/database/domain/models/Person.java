package alvin.database.domain.models;

import java.time.LocalDate;

public class Person {
    private final int id;
    private final String name;
    private final Gender gender;
    private final LocalDate birthday;

    public Person(int id, String name, Gender gender, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(String name, Gender gender, LocalDate birthday) {
        this.id = 0;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
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
