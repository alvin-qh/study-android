package alvin.base.net.socket.jnative.presenters;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.base.net.socket.SocketContracts;
import alvin.base.net.socket.common.models.CommandAck;
import alvin.base.net.socket.jnative.net.SocketNative;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.internal.functions.Functions;

import static alvin.base.net.socket.jnative.NativeModule.Receiver;
import static alvin.base.net.socket.jnative.NativeModule.Send;


public class NativePresenter
        extends PresenterAdapter<SocketContracts.View>
        implements SocketContracts.Presenter {

    private static final int RETRY_TIMES = 3;

    private final RxDecorator rxSendDecorator;
    private final RxDecorator rxReceiverDecorator;

    private SocketNative socket;

    @Inject
    public NativePresenter(@NonNull final SocketContracts.View view,
                           @NonNull @Send final RxDecorator rxSendDecorator,
                           @NonNull @Receiver final RxDecorator rxReceiverDecorator) {
        super(view);
        this.rxSendDecorator = rxSendDecorator;
        this.rxReceiverDecorator = rxReceiverDecorator;
    }

    @Override
    public void connect() {
        rxSendDecorator.<SocketNative>de(
                Single.create(emitter -> {
                    try {
                        emitter.onSuccess(new SocketNative());
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                socket -> {
                    this.socket = socket;
                    with(SocketContracts.View::connectReady);
                    startReceive();
                },
                throwable -> with(SocketContracts.View::showConnectError)
        );
    }

    private void startReceive() {
        if (socket != null && !socket.isClosed()) {
            rxReceiverDecorator.<CommandAck>de(
                    Observable.create(emitter -> {
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
                    })
            )
                    .retry(RETRY_TIMES)
                    .subscribe(
                            this::responseReceived,
                            throwable -> {
                                with(SocketContracts.View::showConnectError);
                                with(SocketContracts.View::disconnected);
                            },
                            () -> with(SocketContracts.View::disconnected)
                    );
        }
    }

    private void responseReceived(CommandAck ack) {
        switch (ack.getCmd()) {
        case "time-ack":
            if (!Strings.isNullOrEmpty(ack.getValue())) {
                LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
                with(view -> view.showRemoteDatetime(time));
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
    public void readRemoteDatetime() {
        if (socket != null && !socket.isClosed()) {
            rxSendDecorator.de(
                    Completable.create(emitter -> {
                        try {
                            socket.getRemoteTime();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    })
            )
                    .retry(RETRY_TIMES)
                    .subscribe(
                            Functions.EMPTY_ACTION,
                            throwable -> with(view -> view.showException(throwable))
                    );
        }
    }

    @Override
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            rxSendDecorator.de(
                    Completable.create(emitter -> {
                        try {
                            socket.disconnect();
                            emitter.onComplete();
                        } catch (IOException e) {
                            socket.close();
                            emitter.onError(e);
                        }
                    })
            )
                    .retry(RETRY_TIMES)
                    .subscribe(
                            Functions.EMPTY_ACTION,
                            throwable -> with(view -> view.showException(throwable))
                    );
        }
    }
}
