package alvin.base.service.working.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.service.working.WorkingContracts;
import alvin.base.service.working.services.WorkingService;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class WorkingPresenter extends ViewPresenterAdapter<WorkingContracts.View>
        implements WorkingContracts.Presenter {

    private WorkingService.OnServiceCallbackListener listener = time ->
            withView(view -> view.showTime(time));

    @Inject
    WorkingPresenter(@NonNull WorkingContracts.View view) {
        super(view);
    }

    @Override
    public WorkingService.OnServiceCallbackListener getCallbackListener() {
        return listener;
    }
}
