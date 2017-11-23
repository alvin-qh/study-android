package alvin.base.mvp.domain;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MainDatabase.NAME, version = MainDatabase.VERSION)
public abstract class MainDatabase {
    static final String NAME = "main";
    static final int VERSION = 1;
}
