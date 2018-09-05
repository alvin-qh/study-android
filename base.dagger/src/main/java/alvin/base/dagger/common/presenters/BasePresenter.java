package alvin.adv.dagger.common.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import alvin.adv.dagger.common.contracts.CommonContracts;
import alvin.adv.dagger.common.domain.models.Message;
import alvin.adv.dagger.common.domain.repositories.MessageRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public abstract class BasePresenter
        extends PresenterAdapter<CommonContracts.View>
        implements CommonContracts.Presenter {

    private final RxDecorator rxDecorator = RxDecorator.newBuilder()
            .subscribeOn(RxSchedulers::database)
            .observeOn(AndroidSchedulers::mainThread)
            .build();

    private final MessageRepository messageRepository;
    private final TransactionManager transactionManager;

    public BasePresenter(@NonNull CommonContracts.View view,
                         @NonNull MessageRepository messageRepository,
                         @NonNull TransactionManager transactionManager) {
        super(view);
        this.messageRepository = messageRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void loadData() {
        Single<List<Message>> single = rxDecorator.de(
                Single.create(emitter -> {
                    try {
                        emitter.onSuccess(messageRepository.findAll());
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
        );

        single.subscribe(
                messageList -> with(view -> view.showMessages(messageList)),
                throwable -> with(view -> view.showException(throwable))
        );
    }

    @Override
    public void createMessage(@NonNull String message) {
        Single<List<Message>> single = rxDecorator.de(
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

        single.subscribe(
                messageList -> with(view -> view.showMessages(messageList)),
                throwable -> with(view -> view.showException(throwable))
        );
    }

    @Override
    public void deleteMessage(int messageId) {
        final Single<List<Message>> subscribe = rxDecorator.de(
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
                messageList -> with(view -> view.showMessages(messageList)),
                throwable -> with(view -> view.showException(throwable))
        );
    }
}
