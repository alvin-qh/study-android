package alvin.base.mvp.main.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.main.MainContracts;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.ViewPresenterAdapter;
import io.reactivex.Single;

public class MainPresenter extends ViewPresenterAdapter<MainContracts.View>
        implements MainContracts.Presenter {

    private NameCardService nameCardService;
    private RxManager rxManager;

    @Inject
    public MainPresenter(@NonNull MainContracts.View view,
                         @NonNull NameCardService nameCardService,
                         @NonNull RxManager rxManager) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxManager = rxManager;
    }

    @Override
    public void onStop() {
        super.onStop();
        rxManager.clear();
    }

    @Override
    public void loadNameCards() {
        SingleSubscriber<List<NameCard>> subscriber = rxManager.with(
                Single.create(emitter -> emitter.onSuccess(nameCardService.loadAll())));

        subscriber.subscribe(nameCards ->
                withView(view ->
                        view.nameCardsLoaded(nameCards)));
    }
}
