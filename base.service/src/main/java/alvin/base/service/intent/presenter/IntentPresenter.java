package alvin.base.service.intent.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.service.intent.IntentContracts;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class IntentPresenter
        extends PresenterAdapter<IntentContracts.View>
        implements IntentContracts.Presenter {

    private int jobId = 1;

    @Inject
    public IntentPresenter(@NonNull IntentContracts.View view) {
        super(view);
    }

    @Override
    public void makeNewJob() {
        with(view -> view.doNewJob("Job_" + jobId++));
    }
}
