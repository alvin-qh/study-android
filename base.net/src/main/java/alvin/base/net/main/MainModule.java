package alvin.adv.net.main;

import alvin.adv.net.main.views.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainModule {

    @ContributesAndroidInjector
    MainActivity activity();
}
