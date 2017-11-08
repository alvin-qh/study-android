package alvin.base.mvp.android.di;

import alvin.base.mvp.android.views.AndroidMainActivity;
import alvin.lib.common.di.scopes.Session;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Session
@Subcomponent
public interface AndroidMainActivityComponent extends AndroidInjector<AndroidMainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AndroidMainActivity> {
    }
}
