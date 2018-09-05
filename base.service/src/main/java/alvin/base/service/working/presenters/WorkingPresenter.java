package alvin.adv.service.working.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.adv.service.working.WorkingContracts;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class WorkingPresenter
        extends PresenterAdapter<WorkingContracts.View>
        implements WorkingContracts.Presenter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Inject
    public WorkingPresenter(@NonNull WorkingContracts.View view) {
        super(view);
    }

    @Override
    public void gotResult(LocalDateTime dateTime) {
        with(view -> view.showResult(formatter.format(dateTime)));
    }
}
