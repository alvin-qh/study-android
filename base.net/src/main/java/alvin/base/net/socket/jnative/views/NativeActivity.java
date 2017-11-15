package alvin.base.net.socket.jnative.views;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.common.views.BaseActivity;
import alvin.base.net.socket.jnative.presenters.NativePresenter;

public class NativeActivity extends BaseActivity {

    @Override
    protected SocketContract.Presenter getPresenter() {
        return new NativePresenter(this);
    }
}
