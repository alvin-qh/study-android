package alvin.base.net.socket.views;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.presenters.SocketNativePresenter;

public class SocketNativeActivity extends SocketBaseActivity {

    @Override
    protected SocketContract.Presenter getPresenter() {
        return new SocketNativePresenter(this);
    }
}
