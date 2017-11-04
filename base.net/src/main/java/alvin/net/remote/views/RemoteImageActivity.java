package alvin.net.remote.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.io.File;
import java.time.temporal.ChronoUnit;

import alvin.common.util.Cache;
import alvin.net.R;
import alvin.net.remote.RemoteImageContract;
import alvin.net.remote.images.RemoteImageLoader;
import alvin.net.remote.presenters.RemoteImagePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RemoteImageActivity extends AppCompatActivity implements RemoteImageContract.View {
    private static final String KEY_IMAGE_SRC = "key_image_src";
    private static final String CACHE_DIR_NAME = "images";

    private static final Cache<Drawable> CACHE = new Cache<>(1L, ChronoUnit.HOURS);

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

        File imageCacheDir = new File(getExternalCacheDir(), CACHE_DIR_NAME);
        if (!imageCacheDir.exists()) {
            imageCacheDir.mkdirs();
        }

        presenter = new RemoteImagePresenter(this);
        presenter.doCreate();

        setContentView(R.layout.activity_remote_image);
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

        CACHE.clear();
    }

    @Override
    public void imageSrcLoaded() {
        PagerAdapter adapter = container.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();

            onImagePageSelected(container.getCurrentItem());
        }
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

        private String imageSrc;

        @BindView(R.id.iv_section)
        ImageView ivSection;

        private Disposable dispLoadImage;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_slide_view, container, false);
            ButterKnife.bind(this, view);

            final Bundle bundle = getArguments();
            if (bundle != null) {
                imageSrc = bundle.getString(KEY_IMAGE_SRC);

                dispLoadImage = Single.<Drawable>create(
                        emitter -> {
                            Context context = getContext();
                            try {
                                Drawable drawable = null;
                                if (context != null) {
                                    String path = new File(getContext().getExternalCacheDir(), CACHE_DIR_NAME).getAbsolutePath();
                                    RemoteImageLoader loader = new RemoteImageLoader(path, CACHE);
                                    drawable = loader.loadImageWithCache(imageSrc);
                                }
                                emitter.onSuccess(drawable);
                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        })
                        .retry(2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(drawable -> {
                            if (drawable != null) {
                                ivSection.setImageDrawable(drawable);
                            }
                        });
            }
            return view;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();

            if (dispLoadImage != null) {
                dispLoadImage.dispose();
            }
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
