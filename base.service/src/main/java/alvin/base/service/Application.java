package alvin.base.service;

import android.app.Activity;
import android.app.Service;
import android.support.multidex.MultiDexApplication;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

public class Application extends MultiDexApplication
        implements HasActivityInjector, HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent.create().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }
}
