package alvin.base.dagger;

import javax.inject.Singleton;

import alvin.base.dagger.android.subcomponent.SubcomponentModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, SubcomponentModule.class})
public interface ApplicationComponent {

    void inject(Application application);
}
