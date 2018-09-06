package alvin.lib.mvp.contracts.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;
import alvin.lib.mvp.contracts.Target;

public abstract class PresenterAdapter<T extends Target> implements IPresenter {

    private final WeakReference<T> targetRef;

    public PresenterAdapter(@NonNull T target) {
        this.targetRef = new WeakReference<>(target);
    }

    protected void with(@NonNull Consumer<T> consumer) {
        T obj = targetRef.get();
        if (obj != null && isValid(obj)) {
            consumer.accept(obj);
        }
    }

    private boolean isValid(T target) {
        if (target instanceof IView) {
            return !((IView)target).isDestroyed();
        }
        return true;
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
