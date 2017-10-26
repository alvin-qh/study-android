package alvin.net.status;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import alvin.net.R;
import alvin.net.status.handlers.NetStatusEventHandler;
import alvin.net.status.handlers.OnNetStatusChangedListener;
import alvin.net.status.network.NetworkCallback;
import alvin.net.status.network.NetworkStatus;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusMainActivity extends AppCompatActivity implements OnNetStatusChangedListener {

    @BindView(R.id.notify_bar)
    View notifyBar;

    @BindView(R.id.notify_icon)
    ImageView notifyIcon;

    @BindView(R.id.text_net_status)
    TextView textNetStatus;

    @BindColor(R.color.bg_notify_info)
    int colorNotifyInfo;

    @BindColor(R.color.bg_notify_error)
    int colorNotifyError;

    @BindColor(R.color.icon_info)
    int colorIconInfo;

    @BindColor(R.color.icon_warning)
    int colorIconWarn;

    private Timer timer = null;

    private NetworkCallback networkCallback;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_main);

        ButterKnife.bind(this);

        NetStatusEventHandler.getInstance().addOnNetStatusChangedListener(this);

        onNetworkStatusChanged(NetworkStatus.getStatus(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = new NetworkCallback(this);
            networkCallback.registerNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                }
            });

//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    Log.d("Hello", "");
//                }
//            }, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onNetworkStatusChanged(NetworkStatus status) {
        switch (status) {
        case NONE:
            textNetStatus.setText(R.string.string_no_network);
            notifyBar.setBackgroundColor(colorNotifyError);
            notifyIcon.setImageResource(android.R.drawable.ic_dialog_alert);
            notifyIcon.setColorFilter(colorIconWarn);
            break;
        case MOBILE:
            textNetStatus.setText(R.string.string_mobile_network);
            notifyBar.setBackgroundColor(colorNotifyInfo);
            notifyIcon.setImageResource(android.R.drawable.ic_dialog_info);
            notifyIcon.setColorFilter(colorIconInfo);
            break;
        case WIFI:
            textNetStatus.setText(R.string.string_wifi_network);
            notifyBar.setBackgroundColor(colorNotifyInfo);
            notifyIcon.setImageResource(android.R.drawable.ic_dialog_info);
            notifyIcon.setColorFilter(colorIconInfo);
            break;
        }
        notifyBar.setVisibility(View.VISIBLE);

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notifyBar.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }
}
