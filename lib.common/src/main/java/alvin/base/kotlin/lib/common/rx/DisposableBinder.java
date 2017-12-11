package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.disposables.Disposable;

public class DisposableBinder implements Disposable {
    private final AtomicReference<Disposable> disposableRef;
    private final AtomicBoolean disposed = new AtomicBoolean(false);

    public DisposableBinder() {
        disposableRef = new AtomicReference<>();
    }

    Disposable bind(@NonNull final Disposable disposable) {
        if (disposableRef.get() != null) {
            throw new IllegalArgumentException("Already bound");
        }

        if (disposed.get()) {
            disposable.dispose();
        } else {
            disposableRef.set(disposable);
        }
        return disposable;
    }

    @Override
    public void dispose() {
        disposed.set(true);

        final Disposable disposable = disposableRef.getAndSet(null);
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public boolean isDisposed() {
        boolean disposed = this.disposed.get();
        if (!disposed) {
            final Disposable disposable = disposableRef.get();

            disposed = (disposable == null || disposable.isDisposed());
            this.disposed.set(disposed);
        }
        return disposed;
    }
}
