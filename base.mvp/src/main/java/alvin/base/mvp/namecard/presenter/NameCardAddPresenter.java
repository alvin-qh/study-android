package alvin.base.mvp.namecard.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class NameCardAddPresenter
        extends PresenterAdapter<NameCardContracts.AddView>
        implements NameCardContracts.AddPresenter {

    private final NameCardService nameCardService;
    private final RxDecorator rxDecorator;

    @Inject
    public NameCardAddPresenter(@NonNull NameCardContracts.AddView view,
                                @NonNull NameCardService nameCardService,
                                @NonNull RxDecorator rxDecorator) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxDecorator = rxDecorator;
    }
}
