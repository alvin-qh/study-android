package alvin.lib.common.dbflow.repositories;

import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.io.Closeable;

public class Transaction implements Closeable, AutoCloseable {

    private final DatabaseWrapper database;

    Transaction(DatabaseWrapper database) {
        this.database = database;
    }

    public void commit() {
        database.setTransactionSuccessful();
    }

    @Override
    public void close() {
        database.endTransaction();
    }
}
