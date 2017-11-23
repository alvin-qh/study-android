package alvin.base.service.lifecycle.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.inject.Inject;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.lifecycle.LifecycleContracts;
import alvin.lib.mvp.ViewPresenterAdapter;

public class LifecyclePresenter extends ViewPresenterAdapter<LifecycleContracts.View>
        implements LifecycleContracts.Presenter {

    private Deque<ServiceConnection> connStack = new ArrayDeque<>();

    private int startedCount = 0;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (!Strings.isNullOrEmpty(action)) {
                switch (action) {
                case ServiceBroadcasts.ACTION_SERVICE_CREATED:
                    withView(LifecycleContracts.View::serviceCreated);
                    break;
                case ServiceBroadcasts.ACTION_SERVICE_DESTROYED:
                    withView(LifecycleContracts.View::serviceDestroyed);
                    break;
                default:
                    break;
                }
            }
        }
    };

    @Inject
    LifecyclePresenter(@NonNull LifecycleContracts.View view) {
        super(view);
    }

    @NonNull
    @Override
    public BroadcastReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void serviceStarted() {
        startedCount = 1;
        refreshStartCount();
    }

    @Override
    public void serviceStoped() {
        startedCount = 0;
        refreshStartCount();
    }

    @Override
    public void serviceBound(@NonNull ServiceConnection conn) {
        connStack.push(conn);
        refreshStartCount();
    }

    @Override
    @Nullable
    public ServiceConnection unbindService() {
        ServiceConnection conn = null;
        if (!connStack.isEmpty()) {
            conn = connStack.pop();
        }
        refreshStartCount();
        return conn;
    }

    private void refreshStartCount() {
        withView(view -> {
            int count = startedCount + connStack.size();
            view.showStartCount(count);
        });
    }
}
