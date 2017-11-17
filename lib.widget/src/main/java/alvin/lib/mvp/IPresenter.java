package alvin.lib.mvp;

public interface IPresenter {

    void onCreate();

    void onDestroy();

    void onPause();

    void onResume();

    void onStart();

    void onStop();
}
