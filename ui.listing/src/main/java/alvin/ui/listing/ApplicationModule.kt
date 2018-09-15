package alvin.ui.listing

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun context(application: Application): Context {
            return application
        }
    }
}
