package alvin.base.mvp.domain.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import alvin.base.mvp.domain.MainDatabase;
import alvin.lib.mvp.IModel;

@Table(database = MainDatabase.class, name = "department")
public class Department extends BaseModel implements IModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    Department() {
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
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
}
