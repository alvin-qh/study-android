package alvin.base.mvp.android.di;

import alvin.base.mvp.android.views.AndroidSubcomponentActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface AndroidSubcomponentActivityComponent extends AndroidInjector<AndroidSubcomponentActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AndroidSubcomponentActivity> { }
}
