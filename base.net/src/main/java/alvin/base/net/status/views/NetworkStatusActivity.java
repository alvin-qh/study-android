package alvin.base.net.status.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import alvin.base.net.R;
import alvin.base.net.status.handlers.NetStatusEventHandler;
import alvin.base.net.status.handlers.OnNetStatusChangedListener;
import alvin.base.net.status.network.NetworkStatus;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkStatusActivity extends AppCompatActivity implements OnNetStatusChangedListener {

    @BindView(R.id.bar_network_status)
    NetworkStatusBar barStatus;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_status);

        ButterKnife.bind(this);

        NetStatusEventHandler.getInstance().addOnNetStatusChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerNetworkStatusCallback();
    }

    private static class NetworkCallback extends ConnectivityManager.NetworkCallback {
        private final WeakReference<Context> contextRef;

        NetworkCallback(Context context) {
            this.contextRef = new WeakReference<>(context);
        }

        @Override
        public void onAvailable(Network network) {
            final Context context = contextRef.get();
            if (context != null) {
                Toast.makeText(context, R.string.string_network_available, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLost(Network network) {
            final Context context = contextRef.get();
            if (context != null) {
                Toast.makeText(context, R.string.string_network_lost, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerNetworkStatusCallback() {
        final NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        final ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        manager.registerNetworkCallback(request, new NetworkCallback(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStatusEventHandler.getInstance().removeOnNetStatusChangedListener(this);
    }

    @Override
    public void onNetworkStatusChanged(NetworkStatus status) {
        barStatus.networkStatusChanged(status);
    }
}
