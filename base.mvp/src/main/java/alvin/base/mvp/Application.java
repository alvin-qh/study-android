package alvin.base.mvp;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import alvin.base.mvp.common.di.ApplicationModule;
import alvin.base.mvp.common.di.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class Application extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);

        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
