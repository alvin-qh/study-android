package alvin.adv.mvp.namecard.presenter;

import javax.inject.Inject;

import alvin.adv.mvp.domain.services.NameCardService;
import alvin.adv.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class NameCardAddPresenter
        extends PresenterAdapter<NameCardContracts.AddView>
        implements NameCardContracts.AddPresenter {

    private final NameCardService nameCardService;
    private final RxDecorator.Builder rxDecoratorBuilder;

    @Inject
    public NameCardAddPresenter(NameCardContracts.AddView view,
                                NameCardService nameCardService,
                                @RxType.IO RxDecorator.Builder rxDecoratorBuilder) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxDecoratorBuilder = rxDecoratorBuilder;
    }
}
