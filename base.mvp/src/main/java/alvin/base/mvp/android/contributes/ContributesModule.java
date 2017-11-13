package alvin.base.mvp.android.contributes;

import javax.inject.Named;

import alvin.base.mvp.android.contributes.views.ContributesActivity;
import alvin.base.mvp.android.contributes.presenters.AndroidContributesActivityPresenter;
import alvin.base.mvp.android.qualifiers.Names;
import alvin.base.mvp.common.Contract;
import dagger.Binds;
import dagger.Module;

@Module(includes = {ContributesModule.BindingModule.class})
public class ContributesModule {

    @Module
    public interface BindingModule {
        @Binds
        @Named(Names.CONTRIBUTES)
        Contract.Presenter bindPresenter(AndroidContributesActivityPresenter presenter);

        @Binds
        @Named(Names.CONTRIBUTES)
        Contract.View bindView(ContributesActivity activity);
    }
}
