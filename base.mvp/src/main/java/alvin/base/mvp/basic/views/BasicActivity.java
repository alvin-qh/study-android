package alvin.base.mvp.basic.views;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.basic.BasicModule;
import alvin.base.mvp.basic.DaggerBasicComponent;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.views.BaseActivity;


@Singleton
public class BasicActivity extends BaseActivity implements Contract.View {

    @Inject
    Contract.Presenter presenter;

    @Override
    protected void inject() {
        DaggerBasicComponent.builder()
                .basicModule(new BasicModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected Contract.Presenter getPresenter() {
        return presenter;
    }
}
