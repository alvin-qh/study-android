package alvin.base.net.main;

import alvin.base.net.main.views.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainModule {

    @ContributesAndroidInjector
    MainActivity activity();
}
