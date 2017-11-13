package alvin.base.kotlin

import alvin.base.kotlin.common.domain.db.MainDatabase
import alvin.base.kotlin.dagger.DaggerModule
import alvin.base.kotlin.dagger.views.DaggerActivity
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module(includes = arrayOf(
        PersistModule::class,
        ViewsRegisterModule::class
))
internal interface ApplicationModule

@Module
internal class PersistModule {

    @Singleton
    @Provides
    internal fun databaseDefinition(): DatabaseDefinition {
        return FlowManager.getDatabase(MainDatabase::class.java)
    }
}

@Module
interface ViewsRegisterModule {

    @ContributesAndroidInjector(modules = arrayOf(DaggerModule::class))
    fun daggerMainActivity(): DaggerActivity
}
