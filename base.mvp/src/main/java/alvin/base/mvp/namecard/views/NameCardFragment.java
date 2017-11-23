package alvin.base.mvp.namecard.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import alvin.base.mvp.namecard.NameCardConstracts;
import dagger.android.support.DaggerFragment;

public class NameCardFragment extends DaggerFragment
        implements NameCardConstracts.View {

    @Inject NameCardConstracts.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
