package alvin.base.mvp.basic.views;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.basic.di.BasicMainActivityModule;
import alvin.base.mvp.basic.di.DaggerBasicMainActivityComponent;
import alvin.base.mvp.basic.presenters.BasicPresenter;
import alvin.base.mvp.common.BaseActivity;
import alvin.base.mvp.common.Constract;

@Singleton
public class BasicMainActivity extends BaseActivity implements Constract.View {

    @Inject
    BasicPresenter presenter;

    @Override
    protected void inject() {
        DaggerBasicMainActivityComponent.builder()
                .basicMainActivityModule(new BasicMainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected Constract.Presenter getPresenter() {
        return presenter;
    }
}
