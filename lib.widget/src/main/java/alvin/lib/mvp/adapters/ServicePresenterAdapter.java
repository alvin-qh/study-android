package alvin.lib.mvp.adapters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

import alvin.lib.mvp.contracts.IService;

public abstract class ServicePresenterAdapter<Service extends IService>
        extends PresenterAdapter<Service> {

    public ServicePresenterAdapter(@NonNull Service service) {
        super(service);
    }

    protected void withService(@NonNull Consumer<Service> consumer) {
        super.with(consumer);
    }

    protected WeakReference<Service> getServiceReference() {
        return super.getReference();
    }
}
