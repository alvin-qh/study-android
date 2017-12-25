package alvin.base.service.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import alvin.base.service.main.views.MainActivity;

@Module
public interface MainModule {

    @ContributesAndroidInjector
    MainActivity activity();
}
