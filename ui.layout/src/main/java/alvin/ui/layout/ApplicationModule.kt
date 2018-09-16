package alvin.ui.layout

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Provides
    @Singleton
    @JvmStatic
    fun context(application: Application): Context {
        return application
    }
}
