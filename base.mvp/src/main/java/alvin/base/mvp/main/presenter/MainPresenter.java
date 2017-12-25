package alvin.base.mvp.main.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.main.MainContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class MainPresenter
        extends PresenterAdapter<MainContracts.View>
        implements MainContracts.Presenter {

    private NameCardService nameCardService;
    private RxDecorator rxDecorator;

    @Inject
    public MainPresenter(@NonNull MainContracts.View view,
                         @NonNull NameCardService nameCardService,
                         @NonNull RxDecorator rxDecorator) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxDecorator = rxDecorator;
    }

    @Override
    public void loadNameCards() {
        rxDecorator.<List<NameCard>>de(
                Single.create(emitter -> emitter.onSuccess(nameCardService.loadAll()))
        ).subscribe(nameCards ->
                with(view -> view.nameCardsLoaded(nameCards)));
    }
}
