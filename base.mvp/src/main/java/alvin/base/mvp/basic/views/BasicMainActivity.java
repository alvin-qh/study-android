package alvin.base.mvp.basic.views;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.basic.di.BasicMainActivityModule;
import alvin.base.mvp.basic.di.DaggerBasicMainActivityComponent;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.views.BaseActivity;


@Singleton
public class BasicMainActivity extends BaseActivity implements Contract.View {

    @Inject
    Contract.Presenter presenter;

    @Override
    protected void inject() {
        DaggerBasicMainActivityComponent.builder()
                .basicMainActivityModule(new BasicMainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected Contract.Presenter getPresenter() {
        return presenter;
    }
}
