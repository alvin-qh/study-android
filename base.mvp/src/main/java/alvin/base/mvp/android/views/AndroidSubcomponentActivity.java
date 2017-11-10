package alvin.base.mvp.android.views;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.mvp.android.di.Names;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.views.BaseActivity;
import dagger.android.AndroidInjection;

public class AndroidSubcomponentActivity extends BaseActivity {

    @Inject
    @Named(Names.SUBCOMPONENT)
    Contract.Presenter presenter;

    @Override
    protected Contract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
