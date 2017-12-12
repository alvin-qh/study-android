package alvin.base.service.remote;

import android.support.annotation.NonNull;

import alvin.base.service.remote.aidls.IRemoteBinder;
import alvin.base.service.remote.aidls.models.JobResponse;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface RemoteContracts {

    interface View extends IView {

        void jobStarted(String name);

        void jobFinished(JobResponse response);
    }

    interface Presenter extends IPresenter {
        void serviceBound(@NonNull IRemoteBinder binder);

        void startWork();

        void serviceUnbound();
    }
}
