package alvin.base.mvp.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import alvin.base.mvp.domain.MainDatabase;
import alvin.lib.mvp.IModel;

@Table(database = MainDatabase.class, name = "department")
public class Department extends BaseModel implements IModel, Parcelable {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    Department() {
    }

    public Department(String name) {
        this(0, name);
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Department(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    @Override
    public String toString() {
        return name == null ? "" : name;
    }
}
