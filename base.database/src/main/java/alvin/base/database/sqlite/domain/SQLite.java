package alvin.base.database.sqlite.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

import com.google.common.base.Strings;

import java.io.Closeable;
import java.util.function.Consumer;
import java.util.function.Function;

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
                        "   birthday DATE" +
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public <R> R query(Function<SQLiteDatabase, R> executor) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return executor.apply(db);
        }
    }

    public void execute(Consumer<SQLiteDatabase> executor) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            db.beginTransaction();
            try {
                executor.accept(db);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    @Override
    public void close() {
        helper.close();
    }

    public static class Builder {
        private Context context;
        private String dbName;

        private Builder() {
        }

        public SQLite build() {
            return new SQLite(context, dbName);
        }

        public Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder withDBName(String dbName) {
            this.dbName = dbName;
            return this;
        }
    }
}
