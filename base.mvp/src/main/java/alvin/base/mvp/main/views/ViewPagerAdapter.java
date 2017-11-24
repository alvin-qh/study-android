package alvin.base.mvp.main.views;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.namecard.views.NameCardAddFragment;
import alvin.base.mvp.namecard.views.NameCardDisplayFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<NameCard> nameCards;

    ViewPagerAdapter(@NonNull FragmentManager fm,
                     @NonNull List<NameCard> nameCards) {
        super(fm);
        this.nameCards = nameCards;
    }

    void update(@NonNull List<NameCard> nameCards) {
        this.nameCards = nameCards;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (position < nameCards.size()) {
            final NameCard nameCard = nameCards.get(position);
            return NameCardDisplayFragment.create(nameCard);
        }
        return NameCardAddFragment.create();
    }

    @Override
    public int getCount() {
        return nameCards.size() + 1;
    }
}
