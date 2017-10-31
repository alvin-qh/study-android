package alvin.kotlin

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowManager

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        FlowManager.init(this)
    }
}