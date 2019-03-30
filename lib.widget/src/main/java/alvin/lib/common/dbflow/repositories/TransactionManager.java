package alvin.lib.common.dbflow.repositories;


import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import androidx.annotation.NonNull;

public class TransactionManager {

    private final DatabaseDefinition database;

    public TransactionManager(@NonNull DatabaseDefinition database) {
        this.database = database;
    }

    @NonNull
    public TransactionWrapper begin() {
        final DatabaseWrapper wrapper = this.database.getWritableDatabase();
        wrapper.beginTransaction();
        return new TransactionWrapper(wrapper);
    }

    public void execute(ITransaction trans) {
        database.executeTransaction(trans);
    }

    public Transaction.Builder executeAsync(ITransaction trans) {
        return database.beginTransactionAsync(trans);
    }
}
