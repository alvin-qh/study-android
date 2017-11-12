package alvin.base.kotlin.common.domain.db

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = MainDatabase.NAME, version = MainDatabase.VERSION)
class MainDatabase {
    companion object {
        const val NAME = "database"
        const val VERSION = 1
    }
}