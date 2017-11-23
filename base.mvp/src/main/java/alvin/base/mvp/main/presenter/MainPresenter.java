package alvin.base.mvp.main.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.main.MainConstracts;
import alvin.lib.mvp.ViewPresenterAdapter;

public class MainPresenter extends ViewPresenterAdapter<MainConstracts.View>
        implements MainConstracts.Presenter {

    @Inject
    public MainPresenter(@NonNull MainConstracts.View view) {
        super(view);
    }
}
