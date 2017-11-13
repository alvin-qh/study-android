package alvin.base.mvp.android.contributes.views;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.mvp.android.qualifiers.Names;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.views.BaseActivity;
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
