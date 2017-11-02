package alvin.net.socket.views;

import alvin.net.socket.SocketContract;
import alvin.net.socket.presenters.SocketNativePresenter;
import alvin.net.socket.presenters.SocketNettyPresenter;

public class SocketNettyActivity extends SocketBaseActivity {

    @Override
    protected SocketContract.Presenter getPresenter() {
        return new SocketNettyPresenter(this);
    }
}
