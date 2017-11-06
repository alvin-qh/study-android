package alvin.net.socket.presenters;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.io.EOFException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import alvin.common.rx.RxManager;
import alvin.common.rx.RxSchedulers;
import alvin.common.rx.RxSubscribe;
import alvin.net.socket.SocketContract;
import alvin.net.socket.models.CommandAck;
import alvin.net.socket.net.SocketNative;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocketNativePresenter implements SocketContract.Presenter {

    private final WeakReference<SocketContract.View> viewRef;
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

    public SocketNativePresenter(@NonNull SocketContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    private void withView(Consumer<SocketContract.View> consumer) {
        SocketContract.View view = viewRef.get();
        if (view != null) {
            consumer.accept(view);
        }
    }

    private void doConnect(Consumer<SocketNative> consumer) {
        final RxSubscribe<SocketNative> subscribe = rxSendManager.createSubscribe();

        subscribe.single(
                null,
                emitter -> {
                    try {
                        emitter.onSuccess(new SocketNative());
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                },
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
            final RxSubscribe<CommandAck> subscribe = rxReceiverManager.createSubscribe();

            subscribe.observable(
                    observable -> observable.retry(3),
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
                    },
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
        }
    }

    @Override
    public void doStarted() {
        doConnect(s -> startReceive());
    }

    @Override
    public void doStop() {
        disconnect();
    }

    @Override
    public void doDestroy() {
        viewRef.clear();
        rxSendManager.clear();
        rxReceiverManager.clear();
    }

    @Override
    public void readRemoteDatetime() {
        if (socket != null && !socket.isClosed()) {
            final RxSubscribe<CommandAck> subscribe = rxSendManager.createSubscribe();

            subscribe.completable(
                    completable -> completable.retry(3),
                    emitter -> {
                        try {
                            socket.getRemoteTime();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    },
                    throwable -> withView(SocketContract.View::showRemoteError)
            );
        }
    }

    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            final RxSubscribe<CommandAck> subscribe = rxSendManager.createSubscribe();

            subscribe.completable(
                    null,
                    emitter -> {
                        try {
                            socket.disconnect();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    },
                    throwable -> withView(SocketContract.View::showRemoteError)
            );
        }
    }
}
