package alvin.lib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class AppCompatActivityView<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P presenter;

    @Override
    public Context context() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract P getPresenter();
}
