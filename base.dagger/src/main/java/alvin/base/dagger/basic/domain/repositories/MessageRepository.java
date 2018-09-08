package alvin.base.dagger.basic.domain.repositories;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.dagger.basic.domain.models.Message;
import alvin.base.dagger.basic.domain.models.Message_Table;
import alvin.lib.common.collect.Collections2;
import alvin.lib.common.dbflow.repositories.BaseRepository;

@Singleton
public class MessageRepository extends BaseRepository<Message> {

    @Inject
    MessageRepository() { }

    public void findAll(Consumer<List<Message>> callback) {
        SQLite.select().from(Message.class)
                .orderBy(Message_Table.timestamp, false)
                .async()
                .queryListResultCallback((tx, messages) -> callback.accept(messages))
                .execute();
    }

    public void findById(int messageId, Consumer<Optional<Message>> callback) {
        SQLite.select().from(Message.class)
                .where(Message_Table.id.eq(messageId))
                .async()
                .queryListResultCallback((tx, messages) -> callback.accept(Collections2.first(messages)))
                .execute();
    }
}
