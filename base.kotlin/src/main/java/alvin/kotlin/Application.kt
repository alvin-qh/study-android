package alvin.kotlin

import android.support.multidex.MultiDexApplication
import com.raizlabs.android.dbflow.config.FlowManager

class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        FlowManager.init(this)
    }
}