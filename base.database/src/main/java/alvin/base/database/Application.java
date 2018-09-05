package alvin.adv.database;

import android.support.multidex.MultiDexApplication;

import com.raizlabs.android.dbflow.config.FlowManager;

import alvin.lib.common.utils.Applications;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize DB Flow
        FlowManager.init(this);

        Applications.startStethoIfDebugging(this);
    }
}
