package alvin.lib.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

abstract class PresenterAdapter<T> implements IPresenter {

    private final WeakReference<T> reference;

    PresenterAdapter(@NonNull T obj) {
        this.reference = new WeakReference<>(obj);
    }

    void with(@NonNull Consumer<T> consumer) {
        T obj = reference.get();
        if (obj != null) {
            consumer.accept(obj);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    @CallSuper
    public void onDestroy() {
        reference.clear();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    protected WeakReference<T> getReference() {
        return reference;
    }
}
