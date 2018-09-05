package alvin.base.service.main;

import alvin.base.service.main.views.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainModule {

    @ContributesAndroidInjector
    MainActivity activity();
}
