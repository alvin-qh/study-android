package alvin.base.dagger.android.contributes.views;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.views.BaseActivity;
import dagger.android.AndroidInjection;

public class ContributesActivity extends BaseActivity {

    @Inject
    @Named(Names.CONTRIBUTES)
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
