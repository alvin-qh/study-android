package alvin.base.kotlin

import alvin.base.kotlin.common.domain.db.MainDatabase
import alvin.lib.common.dbflow.repositories.TransactionManager
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun transactionManager(): TransactionManager {
        return TransactionManager(FlowManager.getDatabase(MainDatabase::class.java))
    }
}
