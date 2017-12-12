package alvin.lib.mvp.contracts;

public interface IPresenter {

    void onCreate();

    void onDestroy();

    void onPause();

    void onResume();

    void onStart();

    void onStop();
}
