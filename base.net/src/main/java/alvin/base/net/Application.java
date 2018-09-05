package alvin.adv.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.IOException;

import alvin.adv.net.status.network.NetworkCallback;
import alvin.adv.net.status.receivers.NetStatusBroadcastReceiver;
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

        NetworkCallback callback = new NetworkCallback(this);
        callback.registerNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Toast.makeText(Application.this, R.string.string_network_available, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                Toast.makeText(Application.this, R.string.string_network_lost, Toast.LENGTH_LONG).show();
            }
        });

        try {
            ApplicationConfig.initialize(this);
        } catch (IOException e) {
            Toast.makeText(this, R.string.error_cannot_load_config_file, Toast.LENGTH_LONG).show();
            System.exit(-1);
        }
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
