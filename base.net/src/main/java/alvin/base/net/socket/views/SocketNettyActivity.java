package alvin.base.net.socket.views;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.presenters.SocketNettyPresenter;

public class SocketNettyActivity extends SocketBaseActivity {

    @Override
    protected SocketContract.Presenter getPresenter() {
        return new SocketNettyPresenter(this);
    }
}
