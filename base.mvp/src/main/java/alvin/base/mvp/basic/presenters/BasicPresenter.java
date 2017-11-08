package alvin.base.mvp.basic.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.basic.domain.repositories.MessageRepository;
import alvin.base.mvp.domain.models.Message;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.PresenterAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static alvin.base.mvp.common.Constract.Presenter;
import static alvin.base.mvp.common.Constract.View;

public class BasicPresenter extends PresenterAdapter<View> implements Presenter {

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(RxSchedulers::database)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    private final MessageRepository messageRepository;

    @Inject
    public BasicPresenter(@NonNull View view,
                          @NonNull MessageRepository messageRepository) {
        super(view);
        this.messageRepository = messageRepository;
    }

    @Override
    public void started() {
        super.started();

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
