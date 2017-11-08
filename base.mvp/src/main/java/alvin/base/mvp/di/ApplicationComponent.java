package alvin.base.mvp.di;

import javax.inject.Singleton;

import alvin.base.mvp.Application;
import alvin.base.mvp.android.di.AndroidMainActivityModule;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidMainActivityModule.class
})
public interface ApplicationComponent {
    void inject(Application application);
}
