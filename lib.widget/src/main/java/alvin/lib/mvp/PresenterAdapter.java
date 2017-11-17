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

    public void onCreate() {
    }

    public void onDestroy() {
        viewRef.clear();
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    protected WeakReference<View> getViewRef() {
        return viewRef;
    }
}
