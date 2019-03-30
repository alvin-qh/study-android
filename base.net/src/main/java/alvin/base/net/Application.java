package alvin.base.net;

import android.os.Build;

import alvin.lib.common.utils.ApplicationConfig;
import alvin.lib.common.utils.Applications;
import androidx.annotation.RequiresApi;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class Application extends DaggerApplication {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate() {
        super.onCreate();

        ApplicationConfig.initialize(this);
        Applications.startStethoIfDebugging(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
