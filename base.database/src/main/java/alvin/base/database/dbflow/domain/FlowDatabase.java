package alvin.base.database.dbflow.domain;

import com.raizlabs.android.dbflow.annotation.Database;

import alvin.base.database.ApplicationModule;

@Database(name = FlowDatabase.NAME, version = FlowDatabase.VERSION)
public abstract class FlowDatabase {
    static final String NAME = ApplicationModule.DB_NAME;
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
