package alvin.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Closeable;

public final class SQLite implements Closeable, AutoCloseable {
    private static final int VERSION = 1;

    private final SQLiteOpenHelper helper;

    private SQLite(Context context, String name) {
        this.helper = new SQLiteOpenHelper(context, name, null, VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String tableUser = "CREATE TABLE IF NOT EXISTS user(" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "gender TEXT," +
                        "birthday TEXT" +
                        ")";

                db.execSQL(tableUser);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // If VERSION has been changed, upgrade schema here
            }
        };
    }

    public static SQLite createSQLiteDB(Context context) {
        return new SQLite(context, "sqlite_db");
    }

    public SQLiteDatabase getReadable() {
        return helper.getReadableDatabase();
    }

    public SQLiteDatabase getWritable() {
        return helper.getWritableDatabase();
    }

    @Override
    public void close() {
        helper.close();
    }
}
