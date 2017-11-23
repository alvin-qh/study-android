package alvin.base.dagger.common.domain.repositories;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import alvin.base.dagger.common.domain.models.Message;
import alvin.base.dagger.common.domain.models.Message_Table;
import alvin.lib.common.collect.Collections2;
import alvin.lib.common.dbflow.repositories.BaseRepository;

public class MessageRepository extends BaseRepository<Message> {

    @Inject
    public MessageRepository() { }

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
}
