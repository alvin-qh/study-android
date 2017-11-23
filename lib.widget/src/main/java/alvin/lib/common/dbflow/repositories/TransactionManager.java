package alvin.lib.common.dbflow.repositories;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

public class TransactionManager {

    private final DatabaseDefinition database;

    public TransactionManager(@NonNull DatabaseDefinition database) {
        this.database = database;
    }

    @NonNull
    public Transaction beginTransaction() {
        final DatabaseWrapper wrapper = this.database.getWritableDatabase();
        wrapper.beginTransaction();
        return new Transaction(wrapper);
    }

    public void executeTransaction(ITransaction trans) {
        database.executeTransaction(trans);
    }
}
