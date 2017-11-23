package alvin.lib.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public abstract class ViewPresenterAdapter<View extends IView> extends PresenterAdapter<View> {

    public ViewPresenterAdapter(@NonNull View view) {
        super(view);
    }

    protected void withView(@NonNull Consumer<View> consumer) {
        super.with(consumer);
    }

    protected WeakReference<View> getViewReference() {
        return super.getReference();
    }
}
