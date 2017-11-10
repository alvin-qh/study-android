package alvin.base.mvp.android.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.mvp.android.di.Names;
import alvin.base.mvp.basic.domain.repositories.MessageRepository;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.presenters.BaseActivityPresenter;

public class AndroidSubcomponentActivityPresenter extends BaseActivityPresenter {

    @Inject
    public AndroidSubcomponentActivityPresenter(@NonNull @Named(Names.SUBCOMPONENT) Contract.View view,
                                                @NonNull MessageRepository messageRepository) {
        super(view, messageRepository);
    }
}
