package alvin.base.service.lifecycle.views;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

class ServiceManager {
    private final Context context;
    private final Intent serviceIntent;

    private int serviceStarted;
    private Deque<WeakReference<ServiceConnection>> connStack = new ArrayDeque<>();

    ServiceManager(Context context, Intent serviceIntent) {
        this.context = context;
        this.serviceIntent = serviceIntent;
    }

    public void start() {
        context.startService(serviceIntent);
        serviceStarted = 1;
    }

    public void stop() {
        context.stopService(serviceIntent);
        serviceStarted = 0;
    }

    public void bind(ServiceConnection conn) {
        context.bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);
        connStack.push(new WeakReference<>(conn));
    }

    public void unbind() {
        if (!connStack.isEmpty()) {
            final ServiceConnection conn = connStack.pop().get();
            if (conn != null) {
                context.unbindService(conn);
            }
        }
    }

    public void remove(ServiceConnection conn) {
        final Iterator<WeakReference<ServiceConnection>> iter = connStack.iterator();
        while (iter.hasNext()) {
            final ServiceConnection raw = iter.next().get();
            if (raw == null || raw == conn) {
                iter.remove();
            }
        }
    }

    public int getStartCount() {
        return serviceStarted + connStack.size();
    }
}
