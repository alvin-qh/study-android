package alvin.base.mvp.common.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import alvin.base.mvp.basic.domain.repositories.MessageRepository;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.domain.models.Message;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.PresenterAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

public abstract class BaseActivityPresenter extends PresenterAdapter<Contract.View> implements Contract.Presenter {

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(RxSchedulers::database)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    private final MessageRepository messageRepository;

    public BaseActivityPresenter(@NonNull Contract.View view,
                                 @NonNull MessageRepository messageRepository) {
        super(view);
        this.messageRepository = messageRepository;
    }

    @Override
    public void onStart() {
        super.onStart();

        final SingleSubscriber<List<Message>> subscribe = rxManager.single(emitter -> {
            try {
                emitter.onSuccess(messageRepository.findAll());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        subscribe.subscribe(
                messageList -> withView(view -> view.showMessages(messageList)),
                throwable -> withView(view -> view.showDefaultError(throwable))
        );
    }

    @Override
    public void createMessage(@NonNull String message) {
        final SingleSubscriber<List<Message>> subscribe = rxManager.single(emitter -> {
            try {
                Message entity = new Message();
                entity.setMessage(message);
                entity.setTimestamp(LocalDateTime.now());

                messageRepository.save(entity);

                emitter.onSuccess(messageRepository.findAll());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        subscribe.subscribe(
                messageList -> withView(view -> view.showMessages(messageList)),
                throwable -> withView(view -> view.showDefaultError(throwable))
        );
    }

    @Override
    public void deleteMessage(int messageId) {
        final SingleSubscriber<List<Message>> subscribe = rxManager.single(emitter -> {
            try {
                messageRepository.findById(messageId).ifPresent(messageRepository::delete);
                emitter.onSuccess(messageRepository.findAll());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        subscribe.subscribe(
                messageList -> withView(view -> view.showMessages(messageList)),
                throwable -> withView(view -> view.showDefaultError(throwable))
        );
    }
}
