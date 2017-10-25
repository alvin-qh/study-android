package alvin.database.dbflow.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.time.LocalDate;

import alvin.database.dbflow.FlowDatabase;
import alvin.database.dbflow.converts.LocalDateConvert;
import alvin.database.models.Gender;
import alvin.database.models.IPerson;

@Table(database = FlowDatabase.class, name = "user")
public class Person extends BaseModel implements IPerson {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    @Column
    private Gender gender;

    @Column(typeConverter = LocalDateConvert.class)
    private LocalDate birthday;

    public Person() {
    }

    public Person(int id, String name, Gender gender, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(String name, Gender gender, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
