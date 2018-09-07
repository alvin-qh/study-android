package alvin.base.net;

import javax.inject.Singleton;

import alvin.base.net.http.HttpModule;
import alvin.base.net.remote.RemoteModule;
import alvin.base.net.socket.SocketModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        HttpModule.class,
        RemoteModule.class,
        SocketModule.class
})
public interface ApplicationComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> { }
}
