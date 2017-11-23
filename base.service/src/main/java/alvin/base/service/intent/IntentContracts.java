package alvin.base.service.intent;

import android.content.BroadcastReceiver;

import alvin.base.service.intent.services.IntentService;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface IntentContracts {

    interface View extends IView {

        void serviceCreated();

        void serviceDestroyed();

        void onJobStart(String jobName);

        void onJobFinish(String jobName, long timeSpend);
    }

    interface Presenter extends IPresenter {

        BroadcastReceiver getBroadcastReceiver();

        String getJobName();

        IntentService.OnJobStatusChangeListener getListener();
    }
}
