package alvin.base.mvp.android.di;

import android.app.Activity;

import alvin.base.mvp.android.views.AndroidMainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {AndroidMainActivityComponent.class})
public abstract class AndroidMainActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(AndroidMainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindActivityInjectorFactory(
            AndroidMainActivityComponent.Builder builder);
}
