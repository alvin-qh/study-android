package alvin.base.dagger.basic.views;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.dagger.basic.BasicModule;
import alvin.base.dagger.basic.DaggerBasicComponent;
import alvin.base.dagger.common.contracts.CommonContracts;
import alvin.base.dagger.common.views.BaseActivity;


@Singleton
public class BasicActivity extends BaseActivity implements CommonContracts.View {

    @Inject
    CommonContracts.Presenter presenter;

    @Override
    protected void inject() {
        DaggerBasicComponent.builder()
                .basicModule(new BasicModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected CommonContracts.Presenter getPresenter() {
        return presenter;
    }
}
