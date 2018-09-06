package alvin.base.database;

import com.raizlabs.android.dbflow.config.FlowManager;

import alvin.lib.common.utils.Applications;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class Application extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize DB Flow
        FlowManager.init(this);

        Applications.startStethoIfDebugging(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
