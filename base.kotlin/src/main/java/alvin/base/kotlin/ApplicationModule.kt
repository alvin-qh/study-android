package alvin.base.kotlin

import alvin.base.kotlin.common.domain.db.MainDatabase
import alvin.base.kotlin.dagger.DaggerMainModule
import alvin.base.kotlin.dagger.views.DaggerMainActivity
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module(includes = arrayOf(
        ApplicationModule.PersistModule::class,
        ApplicationModule.ViewsRegisterModule::class
))
internal interface ApplicationModule {

    @Module
    class PersistModule {

        @Singleton
        @Provides
        internal fun databaseDefinition(): DatabaseDefinition {
            return FlowManager.getDatabase(MainDatabase::class.java)
        }
    }

    @Module
    interface ViewsRegisterModule {

        @ContributesAndroidInjector(modules = arrayOf(DaggerMainModule::class))
        fun daggerMainActivity(): DaggerMainActivity
    }
}
