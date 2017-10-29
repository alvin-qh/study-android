package alvin.net.socket.presenters;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Executors;

import alvin.net.socket.SocketContract;
import alvin.net.socket.net.NativeSocket;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NativeSocketPresenter implements SocketContract.Presenter {

    private final WeakReference<SocketContract.View> viewRef;
    private final Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());

    private NativeSocket socket;
    private Disposable connectSubscribe;
    private Disposable remoteTimeSubscribe;
    private Disposable disconnectSubscribe;

    public NativeSocketPresenter(SocketContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void doStarted() {
        connectSubscribe = Single.<NativeSocket>create(emitter -> {
            try {
                emitter.onSuccess(new NativeSocket());
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
                            Optional.ofNullable(viewRef.get()).ifPresent(SocketContract.View::connectReady);
                        },
                        throwable ->
                                Optional.ofNullable(viewRef.get()).ifPresent(SocketContract.View::showConnectError)
                );
    }

    @Override
    public void doStop() {
        if (connectSubscribe != null && !connectSubscribe.isDisposed()) {
            connectSubscribe.dispose();
        }

        if (remoteTimeSubscribe != null && !remoteTimeSubscribe.isDisposed()) {
            remoteTimeSubscribe.dispose();
        }

        if (disconnectSubscribe != null && !disconnectSubscribe.isDisposed()) {
            disconnectSubscribe.dispose();
        }

        if (this.socket != null) {
            Single.create(emitter -> {
                try {
                    socket.disconnect();
                    emitter.onSuccess(null);
                } catch (IOException e) {
                    emitter.onError(e);
                }
            })
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nil -> socket.close(), throwable -> socket.close());
        }
    }

    @Override
    public void doDestroy() {
        viewRef.clear();
    }

    @Override
    public void readRemoteDatetime() {
        if (socket != null) {
            remoteTimeSubscribe = Single.<LocalDateTime>create(emitter -> {
                try {
                    emitter.onSuccess(socket.getRemoteTime());
                } catch (IOException e) {
                    emitter.onError(e);
                }
            })
                    .retry(3)
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            time ->
                                    Optional.ofNullable(viewRef.get()).ifPresent(view -> view.showRemoteDatetime(time)),
                            throwable -> {
                                socket.close();
                                Optional.ofNullable(viewRef.get()).ifPresent(SocketContract.View::showRemoteError);
                            }

                    );
        }
    }

    @Override
    public void disconnect() {
        if (socket != null) {
            disconnectSubscribe = Single.<Boolean>create(emitter -> {
                try {
                    socket.disconnect();
                    emitter.onSuccess(Boolean.TRUE);
                } catch (IOException e) {
                    emitter.onError(e);
                }
            })
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nil -> socket.close(),
                            throwable -> {
                                socket.close();
                                Optional.ofNullable(viewRef.get()).ifPresent(SocketContract.View::showRemoteError);
                            });
        }
    }
}
