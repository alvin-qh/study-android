package alvin.base.dagger.android.contributes;

import javax.inject.Named;

import alvin.base.dagger.android.contributes.presenters.ContributesPresenter;
import alvin.base.dagger.android.contributes.views.ContributesActivity;
import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.common.contracts.CommonContracts;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ContributesModule {

    // @SomeScopes
    @ContributesAndroidInjector(modules = {ViewModule.class})
    ContributesActivity contributesActivity();

    @Module
    interface ViewModule {
        @Binds
        @Named(Names.CONTRIBUTES)
        CommonContracts.Presenter bindPresenter(ContributesPresenter presenter);

        @Binds
        @Named(Names.CONTRIBUTES)
        CommonContracts.View bindView(ContributesActivity activity);
    }
}
