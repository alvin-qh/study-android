package alvin.base.mvp.android.views;

import android.content.Context;

import javax.inject.Inject;

import alvin.base.mvp.common.BaseActivity;
import alvin.base.mvp.common.Constract;
import dagger.android.AndroidInjection;

public class AndroidMainActivity extends BaseActivity {

    @Inject
    Context context;

    @Override
    protected Constract.Presenter getPresenter() {
        return null;
    }

    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
