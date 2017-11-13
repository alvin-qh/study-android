package alvin.base.mvp.android.subcomponent;

import alvin.base.mvp.android.subcomponent.views.SubcomponentActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface SubcomponentComponent extends AndroidInjector<SubcomponentActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SubcomponentActivity> { }
}
