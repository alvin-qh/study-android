package alvin.adv.database.dbflow.domain.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.time.LocalDate;

import alvin.adv.database.dbflow.domain.FlowDatabase;
import alvin.adv.database.common.domain.models.Gender;
import alvin.adv.database.common.domain.models.IPerson;
import alvin.lib.common.dbflow.converts.LocalDateConvert;

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

    Person() {
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
