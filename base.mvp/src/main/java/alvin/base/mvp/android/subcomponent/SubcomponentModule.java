package alvin.base.mvp.android.subcomponent;

import android.app.Activity;

import javax.inject.Named;

import alvin.base.mvp.android.qualifiers.Names;
import alvin.base.mvp.android.subcomponent.presenters.AndroidSubcomponentActivityPresenter;
import alvin.base.mvp.android.subcomponent.views.SubcomponentActivity;
import alvin.base.mvp.common.Contract;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {SubcomponentComponent.class},
        includes = {SubcomponentModule.BindingModule.class})
public class SubcomponentModule {

    @Module
    public abstract class BindingModule {

        @Binds
        @IntoMap
        @ActivityKey(SubcomponentActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bindAndroidSubcomponentActivity(
                SubcomponentComponent.Builder builder);

        @Binds
        @Named(Names.SUBCOMPONENT)
        abstract Contract.Presenter bindPresenter(AndroidSubcomponentActivityPresenter presenter);

        @Binds
        @Named(Names.SUBCOMPONENT)
        abstract Contract.View bindView(SubcomponentActivity activity);
    }
}
