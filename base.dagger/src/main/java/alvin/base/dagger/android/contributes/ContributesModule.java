package alvin.base.dagger.android.contributes;

import javax.inject.Named;

import alvin.base.dagger.android.contributes.presenters.ContributesPresenter;
import alvin.base.dagger.android.contributes.views.ContributesActivity;
import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.common.Contract;
import dagger.Binds;
import dagger.Module;

@Module(includes = {ContributesModule.BindingModule.class})
public class ContributesModule {

    @Module
    public interface BindingModule {
        @Binds
        @Named(Names.CONTRIBUTES)
        Contract.Presenter bindPresenter(ContributesPresenter presenter);

        @Binds
        @Named(Names.CONTRIBUTES)
        Contract.View bindView(ContributesActivity activity);
    }
}
