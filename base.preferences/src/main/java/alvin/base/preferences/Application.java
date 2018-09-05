package alvin.base.preferences;

import android.support.multidex.MultiDexApplication;

import alvin.lib.common.utils.Applications;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Applications.startStethoIfDebugging(this);
    }
}
