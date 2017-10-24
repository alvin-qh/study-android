package alvin.database.dbflow.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

import alvin.database.dbflow.FlowDatabase;

@Table(database = FlowDatabase.class, name = "user")
public class Person extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String name;

    @Column
    public Gender gender;

    @Column
    public Date birthday;
}
