package alvin.base.dagger;

import com.raizlabs.android.dbflow.config.FlowManager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class Application extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }
}
