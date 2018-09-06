package alvin.base.net.remote;

import alvin.base.net.remote.image.ImageModule;
import alvin.base.net.remote.views.RemoteActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        ImageModule.class
})
public interface RemoteModule {

    @ContributesAndroidInjector
    RemoteActivity remoteActivity();
}
