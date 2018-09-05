package alvin.adv.service.lifecycle.presenters;

import android.content.ServiceConnection;
import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.inject.Inject;

import alvin.adv.service.lifecycle.LifecycleContracts;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class LifecyclePresenter
        extends PresenterAdapter<LifecycleContracts.View>
        implements LifecycleContracts.Presenter {

    private Deque<ServiceConnection> connStack = new ArrayDeque<>();

    private int startedCount = 0;

    @Inject
    LifecyclePresenter(@NonNull LifecycleContracts.View view) {
        super(view);
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
    public void serviceUnbound() {
        if (!connStack.isEmpty()) {
            final ServiceConnection conn = connStack.pop();
            with(view -> view.unbindService(conn));
        }
        refreshStartCount();
    }

    private void refreshStartCount() {
        with(view -> {
            int count = startedCount + connStack.size();
            view.showStartCount(count);
        });
    }
}
