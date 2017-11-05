package alvin.net.remote.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.base.Strings;

import alvin.net.R;
import alvin.net.remote.RemoteImageContract;
import alvin.net.remote.presenters.RemoteImagePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteImageActivity extends AppCompatActivity implements RemoteImageContract.View {
    private static final String KEY_IMAGE_SRC = "key_image_src";

    private RemoteImageContract.Presenter presenter;

    @BindView(R.id.container)
    ViewPager container;

    @BindView(R.id.fab_start)
    FloatingActionButton fabStart;

    @BindView(R.id.fab_end)
    FloatingActionButton fabEnd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_image);

        presenter = new RemoteImagePresenter(this);
        presenter.doCreate();

        ButterKnife.bind(this);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        container.setAdapter(adapter);

        container.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                onImagePageSelected(position);
            }
        });
    }

    protected void onImagePageSelected(int position) {
        if (position > 0) {
            fabStart.setVisibility(View.VISIBLE);
        } else {
            fabStart.setVisibility(View.GONE);
        }

        if (position < presenter.getImageCount() - 1) {
            fabEnd.setVisibility(View.VISIBLE);
        } else {
            fabEnd.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.doStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.doStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void imageSrcLoaded() {
        PagerAdapter adapter = container.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();

            onImagePageSelected(container.getCurrentItem());
        }
    }

    @Override
    public void imageLoadFailed(String imageSrc) {
        final String message = getString(R.string.error_load_image_file, imageSrc);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @OnClick({
            R.id.fab_start,
            R.id.fab_end
    })
    public void onFloatingButtonsClicked(FloatingActionButton b) {
        switch (b.getId()) {
        case R.id.fab_start:
            container.arrowScroll(ViewPager.FOCUS_LEFT);
            break;
        case R.id.fab_end:
            container.arrowScroll(ViewPager.FOCUS_RIGHT);
            break;
        }
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(presenter.getImageSrcAt(position));
        }

        @Override
        public int getCount() {
            return presenter.getImageCount();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        @BindView(R.id.iv_section)
        ImageView ivSection;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_slide_view, container, false);
            ButterKnife.bind(this, view);

            final Bundle bundle = getArguments();
            if (bundle != null) {
                final String imageSrc = bundle.getString(KEY_IMAGE_SRC);

                if (!Strings.isNullOrEmpty(imageSrc)) {
                    RemoteImageActivity activity = (RemoteImageActivity) getActivity();
                    if (activity != null && activity.presenter != null) {
                        activity.presenter.loadImageAsDrawable(imageSrc, drawable -> {
                            if (drawable != null) {
                                ivSection.setImageDrawable(drawable);
                            }
                        });
                    }
                }
            }
            return view;
        }

        @NonNull
        public static PlaceholderFragment newInstance(String imageSrc) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_IMAGE_SRC, imageSrc);

            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);
            return fragment;
        }
    }
}
