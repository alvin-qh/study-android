package alvin.base.service;

import javax.inject.Singleton;

import alvin.lib.common.utils.Versions;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Versions version() {
        return Versions.VERSIONS_O;
    }
}
