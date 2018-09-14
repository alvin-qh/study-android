package alvin.base.net.socket.presenters;

import android.annotation.SuppressLint;

import com.google.common.base.Strings;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.base.net.socket.SocketContracts;
import alvin.base.net.socket.common.commands.CommandAck;
import alvin.base.net.socket.network.NativeSocket;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.internal.functions.Functions;

import static alvin.base.net.socket.SocketContracts.NativeView;


public class SocketNativePresenter extends PresenterAdapter<NativeView>
        implements SocketContracts.NativePresenter {

    private final RxDecorator.Builder rxSendDecoratorBuilder;
    private final RxDecorator.Builder rxReceiverDecoratorBuilder;

    private final NativeSocket socket;

    @Inject
    public SocketNativePresenter(NativeView view,
                                 @RxType.SinglePool RxDecorator.Builder rxSendDecoratorBuilder,
                                 @RxType.New RxDecorator.Builder rxReceiverDecoratorBuilder,
                                 NativeSocket socket) {
        super(view);
        this.rxSendDecoratorBuilder = rxSendDecoratorBuilder;
        this.rxReceiverDecoratorBuilder = rxReceiverDecoratorBuilder;
        this.socket = socket;
    }

    @Override
    @SuppressLint("CheckResult")
    public void connect(String ip) {
        final RxDecorator decorator = rxSendDecoratorBuilder.build();
        decorator.de(
                Completable.create(emitter -> {
                    if (!socket.isClosed()) {
                        emitter.onComplete();
                    }

                    try {
                        this.socket.connect(ip);

                        startReceive();
                        emitter.onComplete();
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                () -> with(NativeView::connectReady),
                throwable -> with(view -> view.errorCaused(throwable))
        );
    }

    @SuppressLint("CheckResult")
    private void startReceive() {
        final RxDecorator decorator = rxReceiverDecoratorBuilder.build();
        decorator.<CommandAck>de(
                Observable.create(emitter -> {
                    try {
                        while (!socket.isClosed() && !emitter.isDisposed()) {
                            final CommandAck resp = socket.getResponse();
                            if (resp == null) {
                                break;
                            }
                            emitter.onNext(resp);
                        }
                        socket.close();
                        emitter.onComplete();
                    } catch (EOFException e) {
                        socket.close();
                        emitter.onComplete();
                    } catch (IOException e) {
                        socket.reconnect();
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                this::responseReceived,
                throwable -> with(view -> view.errorCaused(throwable)),
                () -> with(NativeView::disconnected)
        );
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
            synchronized (this) {
                socket.close();
            }
            with(NativeView::showRemoteBye);
            break;
        default:
            synchronized (this) {
                socket.close();
            }
            with(NativeView::disconnected);
            break;
        }
    }

    @Override
    @SuppressLint("CheckResult")
    public void readRemoteDatetime() {
        final RxDecorator decorator = rxSendDecoratorBuilder.build();
        decorator.de(
                Completable.create(emitter -> {
                    try {
                        socket.getRemoteTime();
                        emitter.onComplete();
                    } catch (IOException e) {
                        socket.reconnect();
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                Functions.EMPTY_ACTION,
                throwable -> with(view -> view.errorCaused(throwable))
        );
    }

    @Override
    @SuppressLint("CheckResult")
    public void bye() {
        final RxDecorator decorator = rxSendDecoratorBuilder.build();
        decorator.de(
                Completable.create(emitter -> {
                    try {
                        socket.bye();
                        emitter.onComplete();
                    } catch (IOException e) {
                        socket.close();
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                Functions.EMPTY_ACTION,
                throwable -> with(view -> view.errorCaused(throwable))
        );
    }
}
