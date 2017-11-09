package alvin.base.mvp.common.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Singleton
    @Provides
    Context applicationContext() {
        return applicationContext;
    }
}
