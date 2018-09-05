package alvin.base.dagger.basic.presenters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.dagger.basic.BasicContracts;
import alvin.base.dagger.basic.domain.models.Message;
import alvin.base.dagger.basic.domain.repositories.MessageRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import io.reactivex.Single;

public class BasicPresenter implements BasicContracts.Presenter {

    private final WeakReference<BasicContracts.View> viewRef;
    private final MessageRepository messageRepository;
    private final TransactionManager transactionManager;
    private final RxDecorator.Builder rxDecoratorBuilder;

    @Inject
    public BasicPresenter(BasicContracts.View view,
                          MessageRepository messageRepository,
                          TransactionManager transactionManager,
                          @RxType.IO RxDecorator.Builder rxDecoratorBuilder) {

        this.viewRef = new WeakReference<>(view);
        this.messageRepository = messageRepository;
        this.transactionManager = transactionManager;
        this.rxDecoratorBuilder = rxDecoratorBuilder;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadData() {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        Single<List<Message>> single = decorator.de(
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

    @SuppressLint("CheckResult")
    @Override
    public void createMessage(@NonNull String message) {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        Single<List<Message>> single = decorator.de(
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

    @SuppressLint("CheckResult")
    @Override
    public void deleteMessage(int messageId) {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        final Single<List<Message>> subscribe = decorator.de(
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

    @Override
    public void onDestroy() {
        viewRef.clear();
    }

    private void with(Consumer<BasicContracts.View> callback) {
        final BasicContracts.View view = viewRef.get();
        if (view != null) {
            callback.accept(view);
        }
    }
}
