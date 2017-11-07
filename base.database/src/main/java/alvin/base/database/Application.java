package alvin.base.database;

import android.support.multidex.MultiDexApplication;

import com.raizlabs.android.dbflow.config.FlowManager;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize DB Flow
        FlowManager.init(this);
    }
}
