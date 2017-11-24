package alvin.base.mvp.namecard.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxManager;
import alvin.lib.mvp.ViewPresenterAdapter;
import dagger.Module;

@Module
public class NameCardEditPresenter extends ViewPresenterAdapter<NameCardContracts.EditView>
        implements NameCardContracts.EditPresenter {

    private final NameCardService nameCardService;
    private final RxManager rxManager;

    @Inject
    public NameCardEditPresenter(@NonNull NameCardContracts.EditView view,
                                 @NonNull NameCardService nameCardService,
                                 @NonNull RxManager rxManager) {
        super(view);
        this.nameCardService = nameCardService;
        this.rxManager = rxManager;
    }
}
