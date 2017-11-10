package alvin.base.mvp.android.di;

import android.app.Activity;

import javax.inject.Named;

import alvin.base.mvp.android.presenters.AndroidSubcomponentActivityPresenter;
import alvin.base.mvp.android.views.AndroidSubcomponentActivity;
import alvin.base.mvp.common.Contract;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {AndroidSubcomponentActivityComponent.class},
        includes = {AndroidSubcomponentActivityModule.BindingModule.class})
public class AndroidSubcomponentActivityModule {

    @Module
    public abstract class BindingModule {

        @Binds
        @IntoMap
        @ActivityKey(AndroidSubcomponentActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bindAndroidSubcomponentActivity(
                AndroidSubcomponentActivityComponent.Builder builder);

        @Binds
        @Named(Names.SUBCOMPONENT)
        abstract Contract.Presenter bindPresenter(AndroidSubcomponentActivityPresenter presenter);

        @Binds
        @Named(Names.SUBCOMPONENT)
        abstract Contract.View bindView(AndroidSubcomponentActivity activity);
    }
}
