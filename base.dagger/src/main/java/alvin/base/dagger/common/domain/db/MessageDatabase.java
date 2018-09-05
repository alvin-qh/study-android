package alvin.adv.dagger.common.domain.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MessageDatabase.NAME, version = MessageDatabase.VERSION)
public final class MessageDatabase {
    private MessageDatabase() {
    }

    static final String NAME = "message_db";
    static final int VERSION = 1;
}
