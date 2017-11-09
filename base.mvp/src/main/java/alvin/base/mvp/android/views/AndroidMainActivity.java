package alvin.base.mvp.android.views;

import android.content.Context;

import javax.inject.Inject;

import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.views.BaseActivity;
import dagger.android.AndroidInjection;

public class AndroidMainActivity extends BaseActivity {

    @Inject
    Context context;

    @Override
    protected Contract.Presenter getPresenter() {
        return null;
    }

    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
