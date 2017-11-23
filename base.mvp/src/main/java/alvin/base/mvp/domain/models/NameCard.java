package alvin.base.mvp.domain.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.time.LocalDate;

import alvin.base.mvp.domain.MainDatabase;
import alvin.lib.common.dbflow.converts.LocalDateConvert;
import alvin.lib.mvp.IModel;

@Table(database = MainDatabase.class, name = "name_card")
public class NameCard extends BaseModel implements IModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    @Column
    private Gender gender;

    @ForeignKey(tableClass = Department.class)
    private Department department;

    @Column(typeConverter = LocalDateConvert.class)
    private LocalDate birthday;

    private String photo;

    NameCard() {
    }

    public NameCard(int id, String name, Gender gender,
                    Department department, LocalDate birthday,
                    String photo) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.birthday = birthday;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
