package alvin.adv.mvp.main;

import android.support.annotation.NonNull;

import java.util.List;

import alvin.adv.mvp.domain.models.NameCard;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface MainContracts {

    interface View extends IView {
        void nameCardsLoaded(@NonNull List<NameCard> nameCards);
    }

    interface Presenter extends IPresenter {
        void loadNameCards();
    }
}
