package alvin.base.mvp.main.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.main.MainContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements MainContracts.View {

    @Inject MainContracts.Presenter presenter;

    @BindView(R.id.rv_name_cards) TabLayout rvNameCards;

    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initializeTableView();

        presenter.onCreate();
    }

    private void initializeTableView() {
        fragmentAdapter = new FragmentAdapter(
                getSupportFragmentManager(), Collections.emptyList());
        tiNameCard.setv
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.loadNameCards();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.onStop();
    }

    @Override
    public void nameCardsLoaded(@NonNull List<NameCard> nameCards) {
        fragmentAdapter.update(nameCards);
    }
}
