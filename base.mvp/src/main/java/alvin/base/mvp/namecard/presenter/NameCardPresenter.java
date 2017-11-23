package alvin.base.mvp.namecard.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.namecard.NameCardConstracts;
import alvin.lib.mvp.ViewPresenterAdapter;
import dagger.Module;

@Module
public class NameCardPresenter extends ViewPresenterAdapter<NameCardConstracts.View>
        implements NameCardConstracts.Presenter {

    @Inject
    public NameCardPresenter(@NonNull NameCardConstracts.View view) {
        super(view);
    }
}
