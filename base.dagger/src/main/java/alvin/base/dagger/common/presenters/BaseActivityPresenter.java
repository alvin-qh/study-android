package alvin.base.dagger.common.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.domain.models.Message;
import alvin.base.dagger.common.domain.repositories.MessageRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public abstract class BaseActivityPresenter extends ViewPresenterAdapter<Contract.View>
        implements Contract.Presenter {

    private final RxManager rxManager = RxManager.newBuilder()
            .subscribeOn(RxSchedulers::database)
            .observeOn(AndroidSchedulers::mainThread)
            .build();

    private final MessageRepository messageRepository;
    private final TransactionManager transactionManager;

    public BaseActivityPresenter(@NonNull Contract.View view,
                                 @NonNull MessageRepository messageRepository,
                                 @NonNull TransactionManager transactionManager) {
        super(view);
        this.messageRepository = messageRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void onStart() {
        super.onStart();

        final SingleSubscriber<List<Message>> subscribe = rxManager.with(
                Single.create(emitter -> {
                    try {
                        emitter.onSuccess(messageRepository.findAll());
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
        );

        subscribe.subscribe(
                messageList -> withView(view -> view.showMessages(messageList)),
                throwable -> withView(view -> view.showException(throwable))
        );
    }

    @Override
    public void createMessage(@NonNull String message) {
        final SingleSubscriber<List<Message>> subscribe = rxManager.with(
                Single.create(emitter -> {
                    try {
                        Message entity = new Message();
                        entity.setMessage(message);
                        entity.setTimestamp(LocalDateTime.now());

                        transactionManager.executeTransaction(db -> entity.save());

                        emitter.onSuccess(messageRepository.findAll());
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
        );

        subscribe.subscribe(
                messageList -> withView(view -> view.showMessages(messageList)),
                throwable -> withView(view -> view.showException(throwable))
        );
    }

    @Override
    public void deleteMessage(int messageId) {
        final SingleSubscriber<List<Message>> subscribe = rxManager.with(
                Single.create(emitter -> {
                    try {
                        messageRepository.findById(messageId)
                                .ifPresent(message ->
                                        transactionManager.executeTransaction(db ->
                                                message.delete()
                                        )
                                );
                        emitter.onSuccess(messageRepository.findAll());
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
        );

        subscribe.subscribe(
                messageList -> withView(view -> view.showMessages(messageList)),
                throwable -> withView(view -> view.showException(throwable))
        );
    }
}
