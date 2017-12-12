package alvin.lib.mvp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class AppCompatActivityView<Presenter extends IPresenter>
        extends DaggerAppCompatActivity implements IView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    protected abstract Presenter presenter();

    @Override
    protected void onDestroy() {
        presenter().onDestroy();
        super.onDestroy();
    }
}
