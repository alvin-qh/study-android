package alvin.base.service.basic.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import javax.inject.Inject;

import alvin.base.service.basic.BasicContracts;
import alvin.base.service.basic.broadcasts.BasicBroadcasts;
import alvin.lib.mvp.PresenterAdapter;

public class BasicPresenter extends PresenterAdapter<BasicContracts.View> implements BasicContracts.Presenter {

    private BroadcastReceiver receiver;

    @Inject
    BasicPresenter(@NonNull BasicContracts.View view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        registerReceiver();
    }

    private void registerReceiver() {
        withView(view -> {
            this.receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final String action = intent.getAction();
                    if (!Strings.isNullOrEmpty(action)) {
                        switch (action) {
                        case BasicBroadcasts.ACTION_SERVICE_STARTED:
                            onServiceStarted();
                            break;
                        case BasicBroadcasts.ACTION_SERVICE_DESTROYED:
                            onServiceDestroyed();
                            break;
                        }
                    }
                }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(BasicBroadcasts.ACTION_SERVICE_STARTED);
            filter.addAction(BasicBroadcasts.ACTION_SERVICE_DESTROYED);
            view.context().registerReceiver(receiver, filter);
        });
    }

    private void onServiceDestroyed() {
        withView(BasicContracts.View::serviceDestroyed);
    }

    private void onServiceStarted() {
        withView(BasicContracts.View::serviceStarted);
    }

    @Override
    public void onStop() {
        super.onStop();

        unregisterService();
    }

    private void unregisterService() {
        if (receiver != null) {
            withView(view -> {
                view.context().unregisterReceiver(receiver);
            });
        }
    }
}
