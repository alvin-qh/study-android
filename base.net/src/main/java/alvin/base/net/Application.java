package alvin.base.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import alvin.base.net.status.receivers.NetStatusBroadcastReceiver;
import alvin.lib.common.utils.ApplicationConfig;
import alvin.lib.common.utils.Applications;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class Application extends DaggerApplication {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate() {
        super.onCreate();

        checkNetworkStatus();

        ApplicationConfig.initialize(this);
        Applications.startStethoIfDebugging(this);
    }

    @SuppressLint({"NewApi", "ObsoleteSdkInt"})
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void checkNetworkStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            this.registerReceiver(new NetStatusBroadcastReceiver(), filter);
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
