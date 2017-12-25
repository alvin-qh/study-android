package alvin.base.net.socket;

import alvin.base.net.socket.jnative.NativeModule;
import alvin.base.net.socket.netty.NettyModule;
import alvin.base.net.socket.views.SocketActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        NativeModule.class,
        NettyModule.class
})
public interface SocketModule {

    @ContributesAndroidInjector
    SocketActivity activity();
}
