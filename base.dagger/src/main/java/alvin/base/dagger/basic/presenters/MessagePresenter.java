package alvin.base.dagger.basic.presenters;


import java.time.LocalDateTime;

import javax.inject.Inject;

import alvin.base.dagger.basic.domain.models.Message;
import alvin.base.dagger.basic.domain.repositories.MessageRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import androidx.annotation.NonNull;

import static alvin.base.dagger.basic.BasicContracts.Presenter;
import static alvin.base.dagger.basic.BasicContracts.View;

public class MessagePresenter extends PresenterAdapter<View> implements Presenter {

    private final MessageRepository messageRepository;
    private final TransactionManager tm;

    @Inject
    MessagePresenter(View view,
                     MessageRepository messageRepository,
                     TransactionManager tm) {
        super(view);
        this.messageRepository = messageRepository;
        this.tm = tm;
    }

    @Override
    public void loadData() {
        messageRepository.findAll(messages -> with(view -> view.showMessages(messages)));
    }

    @Override
    public void createMessage(@NonNull String content) {
        Message message = new Message();
        message.setMessage(content);
        message.setTimestamp(LocalDateTime.now());

        tm.executeAsync(message::save)
                .success(tx -> loadData())
                .build()
                .execute();
    }

    @Override
    public void deleteMessage(int messageId) {
        messageRepository.findById(messageId, mayMessage ->
                tm.executeAsync(db -> mayMessage.ifPresent(message -> message.delete(db)))
                        .success(tx -> loadData())
                        .build()
                        .execute());
    }
}
