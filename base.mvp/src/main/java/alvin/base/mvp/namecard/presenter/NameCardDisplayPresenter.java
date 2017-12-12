package alvin.base.mvp.namecard.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxManager;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class NameCardDisplayPresenter extends ViewPresenterAdapter<NameCardContracts.DisplayView>
        implements NameCardContracts.DisplayPresenter {

    private final NameCardService nameCardService;
    private final RxManager rxManager;

    @Inject
    public NameCardDisplayPresenter(@NonNull NameCardContracts.DisplayView view,
                                    @NonNull NameCardService nameCardService,
                                    @NonNull RxManager rxManager) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxManager = rxManager;
    }
}
