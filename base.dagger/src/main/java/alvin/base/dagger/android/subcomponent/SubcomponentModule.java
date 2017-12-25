package alvin.base.dagger.android.subcomponent;

import android.app.Activity;

import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.android.subcomponent.presenters.SubcomponentPresenter;
import alvin.base.dagger.android.subcomponent.views.SubcomponentActivity;
import alvin.base.dagger.common.contracts.CommonContracts;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {SubcomponentComponent.class})
public interface SubcomponentModule {
    @Binds
    @IntoMap
    @ActivityKey(SubcomponentActivity.class)
    AndroidInjector.Factory<? extends Activity> injectFactory(
            SubcomponentComponent.Builder builder);

    @Binds
    @Named(Names.SUBCOMPONENT)
    CommonContracts.Presenter presenter(SubcomponentPresenter presenter);

    @Binds
    @Named(Names.SUBCOMPONENT)
    CommonContracts.View view(SubcomponentActivity activity);
}
