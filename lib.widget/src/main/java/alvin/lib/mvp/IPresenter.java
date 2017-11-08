package alvin.lib.mvp;

public interface IPresenter {

    void created();

    void destroyed();

    void paused();

    void resumed();

    void started();

    void stoped();
}
