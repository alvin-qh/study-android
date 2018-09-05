package alvin.adv.net;

import javax.inject.Singleton;

import alvin.adv.net.http.HttpModule;
import alvin.adv.net.main.MainModule;
import alvin.adv.net.remote.RemoteModule;
import alvin.adv.net.socket.SocketModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        MainModule.class,
        HttpModule.class,
        RemoteModule.class,
        SocketModule.class
})
public interface ApplicationComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> { }
}
