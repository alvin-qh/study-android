package alvin.base.mvp.android.di;

import javax.inject.Named;

import alvin.base.mvp.android.presenters.AndroidContributesActivityPresenter;
import alvin.base.mvp.android.views.AndroidContributesActivity;
import alvin.base.mvp.common.Contract;
import dagger.Binds;
import dagger.Module;

@Module(includes = {AndroidContributesActivityModule.BindingModule.class})
public class AndroidContributesActivityModule {

    @Module
    public interface BindingModule {
        @Binds
        @Named(Names.CONTRIBUTES)
        Contract.Presenter bindPresenter(AndroidContributesActivityPresenter presenter);

        @Binds
        @Named(Names.CONTRIBUTES)
        Contract.View bindView(AndroidContributesActivity activity);
    }
}
