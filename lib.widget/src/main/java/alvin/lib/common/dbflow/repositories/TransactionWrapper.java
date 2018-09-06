package alvin.lib.common.dbflow.repositories;

import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.io.Closeable;

public class TransactionWrapper implements Closeable, AutoCloseable {

    private final DatabaseWrapper database;

    TransactionWrapper(DatabaseWrapper database) {
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
