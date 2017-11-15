package alvin.base.net.socket.jnative.presenters;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.common.models.CommandAck;
import alvin.base.net.socket.jnative.net.SocketNative;
import alvin.lib.common.rx.CompletableSubscriber;
import alvin.lib.common.rx.ObservableSubscriber;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.PresenterAdapter;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;


public class NativePresenter extends PresenterAdapter<SocketContract.View>
        implements SocketContract.Presenter {

    private static final int RETRY_TIMES = 3;

    private final Scheduler scheduler = RxSchedulers.newSingleThread();

    private final RxManager rxReceiverManager = RxManager.newBuilder()
            .withSubscribeOn(Schedulers::io)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    private final RxManager rxSendManager = RxManager.newBuilder()
            .withSubscribeOn(() -> scheduler)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    private SocketNative socket;

    public NativePresenter(@NonNull SocketContract.View view) {
        super(view);
    }

    private void doConnect(Consumer<SocketNative> consumer) {
        final SingleSubscriber<SocketNative> subscriber = rxSendManager.single(
                emitter -> {
                    try {
                        emitter.onSuccess(new SocketNative());
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                }
        );

        subscriber.subscribe(
                socket -> {
                    this.socket = socket;
                    withView(SocketContract.View::connectReady);
                    consumer.accept(socket);
                },
                throwable -> withView(SocketContract.View::showConnectError)
        );
    }

    private void startReceive() {
        if (socket != null && !socket.isClosed()) {
            final ObservableSubscriber<CommandAck> subscriber = rxReceiverManager.observable(
                    emitter -> {
                        try {
                            while (!socket.isClosed()) {
                                CommandAck ack = socket.getResponse();
                                emitter.onNext(ack);
                            }
                            emitter.onComplete();
                        } catch (EOFException e) {
                            socket.close();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    }
            );

            subscriber
                    .config(observable -> observable.retry(RETRY_TIMES))
                    .subscribe(
                            this::responseReceived,
                            throwable -> {
                                withView(SocketContract.View::showConnectError);
                                withView(SocketContract.View::disconnected);
                            },
                            () -> withView(SocketContract.View::disconnected)
                    );
        }
    }

    private void responseReceived(CommandAck ack) {
        switch (ack.getCmd()) {
        case "time-ack":
            if (!Strings.isNullOrEmpty(ack.getValue())) {
                LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
                withView(view -> view.showRemoteDatetime(time));
            }
            break;
        case "bye-ack":
            socket.close();
            break;
        default:
            socket.close();
            break;
        }
    }

    @Override
    public void started() {
        super.started();
        doConnect(s -> startReceive());
    }

    @Override
    public void stoped() {
        super.stoped();
        disconnect();
    }

    @Override
    public void destroyed() {
        super.destroyed();
        rxSendManager.clear();
        rxReceiverManager.clear();
    }

    @Override
    public void readRemoteDatetime() {
        if (socket != null && !socket.isClosed()) {
            final CompletableSubscriber subscriber = rxSendManager.completable(
                    emitter -> {
                        try {
                            socket.getRemoteTime();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    }
            );

            subscriber
                    .config(completable -> completable.retry(RETRY_TIMES))
                    .subscribe(
                            Functions.EMPTY_ACTION,
                            throwable -> withView(view -> view.showDefaultError(throwable))
                    );
        }
    }

    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            final CompletableSubscriber subscriber = rxSendManager.completable(
                    emitter -> {
                        try {
                            socket.disconnect();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    }
            );

            subscriber
                    .config(completable -> completable.retry(RETRY_TIMES))
                    .subscribe(
                            Functions.EMPTY_ACTION,
                            throwable -> withView(view -> view.showDefaultError(throwable))
                    );
        }
    }
}
