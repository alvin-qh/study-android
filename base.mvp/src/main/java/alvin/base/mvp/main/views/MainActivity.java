package alvin.base.mvp.main.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

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

    private static final int PAGE_LIMIT = 3;
    
    @Inject MainContracts.Presenter presenter;

    @BindView(R.id.vp_name_cards) ViewPager vpNameCards;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initViewPager();

        presenter.onCreate();
    }

    private void initViewPager() {
        vpNameCards.setOffscreenPageLimit(PAGE_LIMIT);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                Collections.emptyList());
        vpNameCards.setAdapter(viewPagerAdapter);
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
        viewPagerAdapter.update(nameCards);
    }
}
