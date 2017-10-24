package alvin.database.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = FlowDatabase.NAME, version = FlowDatabase.VERSION)
public class FlowDatabase {
    public static final String NAME = "dbflow_db";
    public static final int VERSION = 1;
}
