package alvin.base.dagger.android.subcomponent.views;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.common.contracts.CommonContracts;
import alvin.base.dagger.common.views.BaseActivity;
import dagger.android.AndroidInjection;

public class SubcomponentActivity extends BaseActivity {

    @Inject
    @Named(Names.SUBCOMPONENT)
    CommonContracts.Presenter presenter;

    @Override
    protected CommonContracts.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
