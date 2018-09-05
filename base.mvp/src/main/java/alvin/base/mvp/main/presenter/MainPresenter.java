package alvin.base.mvp.main.presenter;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.main.MainContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class MainPresenter
        extends PresenterAdapter<MainContracts.View>
        implements MainContracts.Presenter {

    private NameCardService nameCardService;
    private RxDecorator.Builder rxDecoratorBuilder;

    @Inject
    public MainPresenter(MainContracts.View view,
                         NameCardService nameCardService,
                         @RxType.IO RxDecorator.Builder rxDecoratorBuilder) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxDecoratorBuilder = rxDecoratorBuilder;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNameCards() {
        final Single<List<NameCard>> single =
                Single.create(emitter -> emitter.onSuccess(nameCardService.loadAll()));

        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.de(single).subscribe(nameCards -> with(view -> view.nameCardsLoaded(nameCards)));
    }
}
