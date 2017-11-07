package alvin.base.database.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = FlowDatabase.NAME, version = FlowDatabase.VERSION)
public class FlowDatabase {
    static final String NAME = "dbflow_db";
    static final int VERSION = 2;

//    @Migration(version = 2, database = FlowDatabase.class)
//    public static class Migration2_add_birthday_to_user extends AlterTableMigration<Person> {
//
//        public Migration2_add_birthday_to_user(Class<Person> table) {
//            super(table);
//        }
//
//        @Override
//        public void onPreMigrate() {
//            addColumn(SQLiteType.TEXT, "birthday");
//        }
//    }
}
