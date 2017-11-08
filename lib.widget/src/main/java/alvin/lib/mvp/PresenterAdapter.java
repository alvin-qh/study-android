package alvin.lib.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public abstract class PresenterAdapter<View extends IView> implements IPresenter {

    private final WeakReference<View> viewRef;

    public PresenterAdapter(@NonNull View view) {
        this.viewRef = new WeakReference<>(view);
    }

    protected void withView(@NonNull Consumer<View> consumer) {
        View view = viewRef.get();
        if (view != null) {
            consumer.accept(view);
        }
    }

    public void created() {
    }

    public void destroyed() {
        viewRef.clear();
    }

    public void paused() {
    }

    public void resumed() {
    }

    public void started() {
    }

    public void stoped() {
    }

    protected WeakReference<View> getViewRef() {
        return viewRef;
    }
}
