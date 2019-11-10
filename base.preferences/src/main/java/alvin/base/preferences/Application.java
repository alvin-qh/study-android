package alvin.base.preferences;

import alvin.lib.common.utils.Applications;
import androidx.multidex.MultiDexApplication;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Applications.startStethoIfDebugging(this);
    }
}
