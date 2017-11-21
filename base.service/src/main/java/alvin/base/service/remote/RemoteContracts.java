package alvin.base.service.remote;

import android.content.Context;

import alvin.base.service.remote.aidls.models.JobResponse;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface RemoteContracts {

    interface View extends IView {

        void jobStarted(String name);

        void jobFinished(JobResponse response);
    }

    interface Presenter extends IPresenter {

        void bindService(Context context);

        void unbindService(Context context);

        void startWork();
    }
}
