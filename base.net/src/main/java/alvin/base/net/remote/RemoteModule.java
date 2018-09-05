package alvin.adv.net.remote;

import alvin.adv.net.remote.image.ImageModule;
import alvin.adv.net.remote.views.RemoteActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        ImageModule.class
})
public interface RemoteModule {

    @ContributesAndroidInjector
    RemoteActivity remoteActivity();
}
