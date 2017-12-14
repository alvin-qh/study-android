package alvin.adv.permission

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
}
