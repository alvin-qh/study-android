package alvin.base.mvp.di;

import javax.inject.Singleton;

import alvin.base.mvp.Application;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(Application application);
}
