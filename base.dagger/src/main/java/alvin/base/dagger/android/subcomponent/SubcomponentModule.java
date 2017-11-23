package alvin.base.dagger.android.subcomponent;

import android.app.Activity;

import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.android.subcomponent.presenters.SubcomponentPresenter;
import alvin.base.dagger.android.subcomponent.views.SubcomponentActivity;
import alvin.base.dagger.common.Contract;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {SubcomponentComponent.class},
        includes = {SubcomponentModule.BindingModule.class})
public interface SubcomponentModule {

    @Module
    abstract class BindingModule {

        @Binds
        @IntoMap
        @ActivityKey(SubcomponentActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bindAndroidSubcomponentActivity(
                SubcomponentComponent.Builder builder);

        @Binds
        @Named(Names.SUBCOMPONENT)
        abstract Contract.Presenter bindPresenter(SubcomponentPresenter presenter);

        @Binds
        @Named(Names.SUBCOMPONENT)
        abstract Contract.View bindView(SubcomponentActivity activity);
    }
}
