package alvin.base.net.status;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import alvin.base.net.R;
import alvin.base.net.status.handlers.NetStatusEventHandler;
import alvin.base.net.status.handlers.OnNetStatusChangedListener;
import alvin.base.net.status.network.NetworkStatus;
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

    private Animation animFadeIn;
    private Animation animFadeOut;
    private Timer timer;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_main);

        ButterKnife.bind(this);

        NetStatusEventHandler.getInstance().addOnNetStatusChangedListener(this);

        this.animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        this.animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                notifyBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        notifyBar.startAnimation(animFadeOut);
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notifyBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStatusEventHandler.getInstance().removeOnNetStatusChangedListener(this);

        animFadeIn.cancel();
        animFadeIn.setAnimationListener(null);
        animFadeOut.cancel();
        animFadeOut.setAnimationListener(null);

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
        notifyBar.startAnimation(animFadeIn);
    }
}
