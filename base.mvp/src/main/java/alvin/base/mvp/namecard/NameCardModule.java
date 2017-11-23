package alvin.base.mvp.namecard;

import alvin.base.mvp.namecard.presenter.NameCardPresenter;
import alvin.base.mvp.namecard.views.NameCardFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface NameCardModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    NameCardFragment nameCardFragment();

    @Module
    interface ViewModule {

        @Binds
        NameCardConstracts.View view(NameCardFragment fragment);

        @Binds
        NameCardConstracts.Presenter presenter(NameCardPresenter presenter);
    }
}
