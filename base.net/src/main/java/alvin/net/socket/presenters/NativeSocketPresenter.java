package alvin.net.socket.presenters;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.io.EOFException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import alvin.net.socket.SocketContract;
import alvin.net.socket.models.CommandAck;
import alvin.net.socket.net.SocketNative;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NativeSocketPresenter implements SocketContract.Presenter {

    private final WeakReference<SocketContract.View> viewRef;
    private final Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());

    private SocketNative socket;

    private Disposable connectSubscribe;
    private Disposable receiverSubscribe;
    private Disposable remoteTimeSubscribe;
    private Disposable disconnectSubscribe;

    public NativeSocketPresenter(@NonNull SocketContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    private void toView(Consumer<SocketContract.View> consumer) {
        Optional.ofNullable(viewRef.get()).ifPresent(consumer);
    }

    private void doConnect(Consumer<SocketNative> consumer) {
        connectSubscribe = Single.<SocketNative>create(
                emitter -> {
                    try {
                        emitter.onSuccess(new SocketNative());
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                })
                .retry(3)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        socket -> {
                            this.socket = socket;
                            toView(SocketContract.View::connectReady);
                            consumer.accept(socket);
                        },
                        throwable -> toView(SocketContract.View::showConnectError)
                );
    }

    private void startReceive() {
        if (socket != null && !socket.isClosed()) {
            receiverSubscribe = Observable.<CommandAck>create(
                    emitter -> {
                        try {
                            while (!socket.isClosed()) {
                                CommandAck ack = socket.getResponse();
                                emitter.onNext(ack);
                            }
                            emitter.onComplete();
                        } catch (EOFException e) {
                            emitter.onComplete();
                        } catch (IOException e) {
                            emitter.onError(e);
                        }
                    })
                    .retry(3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::responseReceived,
                            throwable -> {
                                toView(SocketContract.View::showConnectError);
                                socket.close();
                            }
                    );
        }
    }

    private void responseReceived(CommandAck ack) {
        switch (ack.getCmd()) {
        case "time-ack":
            if (!Strings.isNullOrEmpty(ack.getValue())) {
                LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
                toView(view -> view.showRemoteDatetime(time));
            }
            break;
        case "bye-ack":
            try {
                socket.close();
            } catch (IOException ignore) {
            }
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

        if (connectSubscribe != null && !connectSubscribe.isDisposed()) {
            connectSubscribe.dispose();
        }

        if (receiverSubscribe != null && !receiverSubscribe.isDisposed()) {
            receiverSubscribe.dispose();
        }

        if (remoteTimeSubscribe != null && !remoteTimeSubscribe.isDisposed()) {
            remoteTimeSubscribe.dispose();
        }

        if (disconnectSubscribe != null && !disconnectSubscribe.isDisposed()) {
            disconnectSubscribe.dispose();
        }
    }

    @Override
    public void readRemoteDatetime() {
        if (socket != null && !socket.isClosed()) {

            remoteTimeSubscribe = Single.create(
                    emitter -> {
                        try {
                            socket.getRemoteTime();
                            emitter.onSuccess(Boolean.TRUE);
                        } catch (IOException e) {
                            emitter.onError(e);
                        }
                    })
                    .retry(3)
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            nil -> {
                            },
                            throwable -> {
                                socket.close();
                                toView(SocketContract.View::showRemoteError);
                            }

                    );
        }
    }

    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {

            disconnectSubscribe = Completable.create(
                    emitter -> {
                        try {
                            socket.disconnect();
                            emitter.onComplete();
                        } catch (IOException e) {
                            emitter.onError(e);
                        }
                    })
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                            },
                            throwable -> {
                                socket.close();
                                toView(SocketContract.View::showRemoteError);
                            });
        }
    }
}
