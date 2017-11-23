package alvin.base.mvp.main.views;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import alvin.base.mvp.domain.models.NameCard;

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<NameCard> nameCards;

    public FragmentAdapter(@NonNull FragmentManager fm,
                           @NonNull List<NameCard> nameCards) {
        super(fm);
        this.nameCards = nameCards;
    }

    public void update(@NonNull List<NameCard> nameCards) {
        this.nameCards = nameCards;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return nameCards.size() + 1;
    }
}
