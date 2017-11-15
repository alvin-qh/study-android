package alvin.base.net.socket.netty.views;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.common.views.BaseActivity;
import alvin.base.net.socket.netty.presenters.NettyPresenter;

public class NettyActivity extends BaseActivity {

    @Override
    protected SocketContract.Presenter getPresenter() {
        return new NettyPresenter(this);
    }
}
