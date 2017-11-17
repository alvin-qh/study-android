package alvin.base.service.basic.services;

import android.os.Binder;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class BasicServiceBinder extends Binder {

    private WeakReference<BasicService> serviceRef;

    public BasicServiceBinder(BasicService service) {
        this.serviceRef = new WeakReference<>(service);
    }

    public void BasicServiceBinder() {
        withService(BasicService::stopMySelf);
    }

    private void withService(@NonNull final Consumer<BasicService> fn) {
        final BasicService service = this.serviceRef.get();
        if (service != null) {
            fn.accept(service);
        }
    }
}
