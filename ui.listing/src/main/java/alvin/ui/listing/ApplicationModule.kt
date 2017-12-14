package alvin.ui.listing

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun context(application: Application): Context {
        return application;
    }
}
