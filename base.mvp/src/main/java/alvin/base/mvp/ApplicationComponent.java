package alvin.base.mvp;

import javax.inject.Singleton;

import alvin.base.mvp.android.subcomponent.SubcomponentModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, SubcomponentModule.class})
public interface ApplicationComponent {

    void inject(Application application);
}
