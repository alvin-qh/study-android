package alvin.base.net;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
interface ApplicationModule {

    @Singleton
    @Provides
    static Context context(final Application application) {
        return application;
    }
}
