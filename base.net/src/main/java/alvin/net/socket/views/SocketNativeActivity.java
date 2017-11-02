package alvin.net.socket.views;

import alvin.net.socket.SocketContract;
import alvin.net.socket.presenters.SocketNativePresenter;

public class SocketNativeActivity extends SocketBaseActivity {

    @Override
    protected SocketContract.Presenter getPresenter() {
        return new SocketNativePresenter(this);
    }
}