package alvin.base.net.status.views;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import alvin.base.net.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class NetworkStatusBar extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    private Animation animFadeIn;
    private Animation animFadeOut;

    private final Handler handler = new Handler();

    public NetworkStatusBar(Context context) {
        super(context);
        init();
    }

    public NetworkStatusBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetworkStatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final Resources resources = getResources();

        initLayout(resources);

        initIcon(resources);
        initTextView(resources);

        initAnimation();
    }

    private void initLayout(Resources r) {
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(r.getColor(R.color.bg_notify_info, null));

        final DisplayMetrics dm = r.getDisplayMetrics();
        final int padding = dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT * 10;

        this.setPaddingRelative(padding, padding, padding, padding);
        this.setVisibility(INVISIBLE);
    }

    private void initIcon(Resources r) {
        final DisplayMetrics dm = r.getDisplayMetrics();
        final int margin = dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT * 15;

        final LayoutParams lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lp.setMarginStart(margin);

        this.imageView = new AppCompatImageView(getContext());
        this.imageView.setColorFilter(r.getColor(R.color.icon_warning, null));
        this.imageView.setImageDrawable(r.getDrawable(android.R.drawable.ic_dialog_alert, null));

        addView(this.imageView, lp);
    }

    private void initTextView(Resources r) {
        final DisplayMetrics dm = r.getDisplayMetrics();
        final int margin = dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT * 15;
        final int padding = dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT * 5;

        final LayoutParams lp = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        lp.setMarginStart(margin);

        this.textView = new AppCompatTextView(getContext());
        this.textView.setPaddingRelative(padding, padding, padding, padding);
        this.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addView(this.textView, lp);
    }

    private void initAnimation() {
        this.animFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        this.animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(() -> startAnimation(animFadeOut), 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        this.animFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        this.animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animFadeOut.setAnimationListener(null);
        animFadeOut.cancel();
        animFadeIn.setAnimationListener(null);
        animFadeIn.cancel();

        handler.removeCallbacksAndMessages(null);
    }

    public void showNetworkStatusChangeMessage(String name, boolean isConnected) {
        final Resources r = getResources();

        if (isConnected) {
            setBackgroundColor(r.getColor(R.color.bg_notify_info, null));
            imageView.setImageResource(android.R.drawable.ic_dialog_info);
            imageView.setColorFilter(R.color.icon_info);
        } else {
            setBackgroundColor(r.getColor(R.color.bg_notify_error, null));
            imageView.setImageResource(android.R.drawable.ic_dialog_alert);
            imageView.setColorFilter(R.color.icon_warning);
        }
        textView.setText(r.getString(R.string.string_network, name, isConnected ? "Connected" : "Disconnected"));

        startAnimation(animFadeIn);
    }
}
