package alvin.base.mvp.namecard.presenter;

import javax.inject.Inject;

import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class NameCardDisplayPresenter
        extends PresenterAdapter<NameCardContracts.DisplayView>
        implements NameCardContracts.DisplayPresenter {

    private final NameCardService nameCardService;
    private final RxDecorator.Builder rxDecoratorBuilder;

    @Inject
    public NameCardDisplayPresenter(NameCardContracts.DisplayView view,
                                    NameCardService nameCardService,
                                    @RxType.IO RxDecorator.Builder rxDecoratorBuilder) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxDecoratorBuilder = rxDecoratorBuilder;
    }
}
