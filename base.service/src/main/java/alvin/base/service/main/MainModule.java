package alvin.adv.service.main;

import alvin.adv.service.main.views.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainModule {

    @ContributesAndroidInjector
    MainActivity activity();
}
