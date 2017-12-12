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
import alvin.lib.mvp.adapters.ViewPresenterAdapter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NativePresenter extends ViewPresenterAdapter<SocketContract.View>
        implements SocketContract.Presenter {

    private static final int RETRY_TIMES = 3;

    private final Scheduler scheduler = RxSchedulers.newSingleThread();

    private final RxManager rxReceiverManager = RxManager.newBuilder()
            .subscribeOn(Schedulers::io)
            .observeOn(AndroidSchedulers::mainThread)
            .build();

    private final RxManager rxSendManager = RxManager.newBuilder()
            .subscribeOn(() -> scheduler)
            .observeOn(AndroidSchedulers::mainThread)
            .build();

    private SocketNative socket;

    public NativePresenter(@NonNull SocketContract.View view) {
        super(view);
    }

    private void doConnect(Consumer<SocketNative> consumer) {
        final SingleSubscriber<SocketNative> subscriber = rxSendManager.with(
                Single.create(emitter -> {
                    try {
                        emitter.onSuccess(new SocketNative());
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                })
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
            final ObservableSubscriber<CommandAck> subscriber = rxReceiverManager.with(
                    Observable.<CommandAck>create(emitter -> {
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
                    }).retry(RETRY_TIMES)
            );

            subscriber.subscribe(
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
    public void onStart() {
        super.onStart();
        doConnect(s -> startReceive());
    }

    @Override
    public void onStop() {
        super.onStop();
        disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rxSendManager.clear();
        rxReceiverManager.clear();
    }

    @Override
    public void readRemoteDatetime() {
        if (socket != null && !socket.isClosed()) {
            final CompletableSubscriber subscriber = rxSendManager.with(
                    Completable.create(emitter -> {
                        try {
                            socket.getRemoteTime();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    }).retry(RETRY_TIMES)
            );

            subscriber.subscribe(throwable ->
                    withView(view -> view.showException(throwable)));
        }
    }

    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            final CompletableSubscriber subscriber = rxSendManager.with(
                    Completable.create(emitter -> {
                        try {
                            socket.disconnect();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    }).retry(RETRY_TIMES)
            );

            subscriber.subscribe(throwable ->
                    withView(view -> view.showException(throwable)));
        }
    }
}
