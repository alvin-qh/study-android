package alvin.base.mvp.basic.domain.repositories;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.Message;
import alvin.base.mvp.domain.models.Message_Table;
import alvin.lib.common.collect.Collections2;

public class MessageRepository {

    private final DatabaseDefinition database;

    @Inject
    public MessageRepository(DatabaseDefinition database) {
        this.database = database;
    }

    public void save(Message message) {
        database.executeTransaction(db -> message.save());
    }

    public List<Message> findAll() {
        return SQLite.select().from(Message.class)
                .orderBy(Message_Table.timestamp, false)
                .queryList();
    }

    public Optional<Message> findById(int messageId) {
        return Collections2.first(
                SQLite.select().from(Message.class)
                        .where(Message_Table.id.eq(messageId))
                        .queryList()
        );
    }

    public void delete(Message message) {
        database.executeTransaction(db -> message.delete());
    }
}
