package alvin.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

import com.google.common.base.Strings;

import java.io.Closeable;

public final class SQLite implements Closeable, AutoCloseable {
    private static final String TAG = SQLite.class.getSimpleName();

    private static final int VERSION = 2;

    private final SQLiteOpenHelper helper;

    private SQLite(Context context, String name) {
        this.helper = new SQLiteOpenHelper(context, name, null, VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                final SparseArray<String> sqls = new SparseArray<>();
                sqls.put(1, "CREATE TABLE IF NOT EXISTS user(" +
                        "   id INTEGER PRIMARY KEY," +
                        "   name TEXT," +
                        "   gender TEXT" +
                        ")");

                sqls.put(2, "CREATE TABLE IF NOT EXISTS user(" +
                        "   id INTEGER PRIMARY KEY," +
                        "   name TEXT," +
                        "   gender TEXT," +
                        "   birthday TEXT" +
                        ")");
                db.execSQL(sqls.get(VERSION));
                Log.d(TAG, "Table user created");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                String sql = null;
                if (oldVersion == 1 && newVersion == 2) {
                    sql = "ALTER TABLE user ADD birthday TEXT";
                }
                if (!Strings.isNullOrEmpty(sql)) {
                    db.execSQL(sql);
                    Log.d(TAG, "Table user updated from version " + oldVersion + " to version " + newVersion);
                }
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
