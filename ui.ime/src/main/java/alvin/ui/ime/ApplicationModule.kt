package alvin.ui.ime

import alvin.lib.common.utils.SystemServices
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun context(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun systemServices(context: Context): SystemServices {
        return SystemServices(context)
    }
}
