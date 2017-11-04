package alvin.net.remote.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alvin.common.util.Values;
import alvin.net.R;
import alvin.net.remote.RemoteImageContract;
import alvin.net.remote.presenters.RemoteImagePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RemoteImageActivity extends AppCompatActivity implements RemoteImageContract.View {
    private static final String ARG_IMAGE_URL = "arg_image_url";

    private final RemoteImageContract.Presenter presenter = new RemoteImagePresenter();

    @BindView(R.id.container)
    ViewPager container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remote_image);

        ButterKnife.bind(this);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        this.container.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(presenter.getImageUrlAt(position));
        }

        @Override
        public int getCount() {
            return presenter.getImageUrls().size();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        @BindView(R.id.tv_section)
        TextView tvSection;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle bundle) {
            View view = inflater.inflate(R.layout.fragment_main2, container, false);
            ButterKnife.bind(this, view);

            bundle = getArguments();

            if (bundle != null) {
                tvSection.setText(Values.safeGet(bundle.getString(ARG_IMAGE_URL), ""));
            }

            return view;
        }

        public static PlaceholderFragment newInstance(String imageUrl) {

            final Bundle bundle = new Bundle();
            bundle.putString(ARG_IMAGE_URL, imageUrl);

            final PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);

            return fragment;
        }
    }
}
