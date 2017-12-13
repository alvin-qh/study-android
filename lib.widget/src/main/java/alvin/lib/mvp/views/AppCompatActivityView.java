package alvin.lib.mvp.views;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class AppCompatActivityView<Presenter extends IPresenter>
        extends DaggerAppCompatActivity implements IView {

    @Inject Presenter presenter;

    @Override
    @CallSuper
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @NonNull
    protected Presenter getPresenter() {
        return presenter;
    }
}
