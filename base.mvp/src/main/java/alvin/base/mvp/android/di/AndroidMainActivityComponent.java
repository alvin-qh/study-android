package alvin.base.mvp.android.di;

import alvin.base.mvp.android.views.AndroidMainActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidMainActivityModule.class})
public interface AndroidMainActivityComponent {

    void inject(AndroidMainActivity activity);

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<AndroidMainActivity> {
    }
}
