package alvin.base.service.messenger.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MessengerHandler messengerHandler = new MessengerHandler(this);
    private final Task task = new Task(1000, 3000);

    private static class MessengerHandler extends Handler {
        private final WeakReference<MessengerService> serviceRef;

        private MessengerHandler(MessengerService service) {
            this.serviceRef = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            final MessengerService service = serviceRef.get();
            if (service == null) {
                return;
            }

            switch (msg.what) {
            case Messages.WHAT_RUN_JOB:
                service.executeJob(msg);
                break;
            default:
                break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(messengerHandler).getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        messengerHandler.removeCallbacksAndMessages(null);
    }

    private void executeJob(final Message msg) {
        final Messenger replyTo = msg.replyTo;
        final Bundle bundle = (Bundle) msg.obj;

        final String name = bundle.getString("name");
        executor.submit(() -> {
            if (replyTo != null) {
                try {
                    replyTo.send(Messages.makeJobStartedAckMessage(name));
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot reply message");
                }
            }

            task.work();

            if (replyTo != null) {
                try {
                    replyTo.send(Messages.makeJobCompleteAckMessage(name));
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot reply message");
                }
            }
        });
    }
}
