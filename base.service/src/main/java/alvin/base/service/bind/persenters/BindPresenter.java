package alvin.adv.service.bind.persenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.adv.service.bind.BindContracts;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class BindPresenter
        extends PresenterAdapter<BindContracts.View>
        implements BindContracts.Presenter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Inject
    public BindPresenter(@NonNull BindContracts.View view) {
        super(view);
    }

    @Override
    public void gotResult(LocalDateTime dateTime) {
        with(view -> view.showResult(dateTime.format(formatter)));
    }
}
