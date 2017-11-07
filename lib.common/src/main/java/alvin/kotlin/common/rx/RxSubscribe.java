package alvin.kotlin.common.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;

public abstract class RxSubscribe implements Disposable {
    private final RxManager rxManager;
    private Disposable disposable;

    RxSubscribe(@NonNull RxManager rxManager) {
        this.rxManager = rxManager;
    }

    @NonNull
    Disposable registerDisposable(@NonNull Disposable disposable) {
        this.disposable = disposable;
        rxManager.register(disposable);
        return disposable;
    }

    @Nullable
    Disposable unregisterDisposable() {
        if (disposable != null) {
            rxManager.unregister(disposable);
        }
        return disposable;
    }

    @Override
    public void dispose() {
        if (!isDisposed()) {
            rxManager.unregister(disposable);
            disposable.dispose();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposable != null && disposable.isDisposed();
    }
}
