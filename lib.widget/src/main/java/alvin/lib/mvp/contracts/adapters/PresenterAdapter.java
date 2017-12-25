package alvin.lib.mvp.contracts.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.Target;

public abstract class PresenterAdapter<T extends Target> implements IPresenter {

    private final WeakReference<T> targetRef;

    public PresenterAdapter(@NonNull T target) {
        this.targetRef = new WeakReference<>(target);
    }

    protected void with(@NonNull Consumer<T> consumer) {
        T obj = targetRef.get();
        if (obj != null) {
            consumer.accept(obj);
        }
    }

    public WeakReference<T> getTargetRef() {
        return targetRef;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        targetRef.clear();
    }
}
