package alvin.base.mvp.di;

import android.app.Activity;
import android.content.Context;

import alvin.base.mvp.android.di.AndroidMainActivityComponent;
import alvin.base.mvp.android.views.AndroidMainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        AndroidMainActivityComponent.class
})
public abstract class ApplicationModule {

    private final Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Binds
    @IntoMap
    @ActivityKey(AndroidMainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindFactory(AndroidMainActivityComponent.Builder builder);
}
