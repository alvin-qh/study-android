package alvin.base.mvp;

import javax.inject.Singleton;

import alvin.base.mvp.android.di.AndroidSubcomponentActivityModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AndroidSubcomponentActivityModule.class})
public interface ApplicationComponent {

    void inject(Application application);
}
