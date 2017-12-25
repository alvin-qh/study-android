package alvin.lib.mvp.contracts.adapters;

import android.support.annotation.CallSuper;

import javax.inject.Inject;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class ActivityAdapter<Presenter extends IPresenter>
        extends DaggerAppCompatActivity implements IView {

    @Inject protected Presenter presenter;

    @Override
    @CallSuper
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
