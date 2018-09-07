package alvin.base.net.socket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import alvin.base.net.socket.common.config.NetworkConfig;
import alvin.base.net.socket.network.NativeProtocol;
import alvin.base.net.socket.network.NativeSocket;
import alvin.base.net.socket.network.NettyProtocol;
import alvin.base.net.socket.network.NettySocket;
import alvin.base.net.socket.presenters.SocketNativePresenter;
import alvin.base.net.socket.presenters.SocketNettyPresenter;
import alvin.base.net.socket.views.SocketNativeActivity;
import alvin.base.net.socket.views.SocketNettyActivity;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.RxType;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static alvin.base.net.socket.SocketContracts.NativePresenter;
import static alvin.base.net.socket.SocketContracts.NativeView;

@Module
public interface SocketModule {

    @Module
    class CommonModule {
        private static final int RETRY_TIMES = 3;

        @Provides
        @RxType.New
        RxDecorator.Builder singleRxDecoratorBuilder() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::newThread)
                    .observeOn(AndroidSchedulers::mainThread)
                    .retryTimes(RETRY_TIMES);
        }

        @Provides
        @RxType.SinglePool
        RxDecorator.Builder ioRxReceiverDecoratorBuilder() {
            return RxDecorator.newBuilder()
                    .subscribeOn(RxSchedulers::singlePool)
                    .observeOn(AndroidSchedulers::mainThread)
                    .retryTimes(RETRY_TIMES);
        }

        @Provides
        NetworkConfig networkConfig() {
            return new NetworkConfig();
        }

        @Provides
        ObjectMapper mapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper;
        }
    }

    @ContributesAndroidInjector(modules = {CommonModule.class, NativeModule.class})
    SocketNativeActivity socketNativeActivity();

    @Module(includes = {
            NativeModule.BindModule.class,
            NativeModule.ProvidersModule.class})
    interface NativeModule {

        @Module
        interface BindModule {
            @Binds
            NativeView view(SocketNativeActivity activity);

            @Binds
            NativePresenter presenter(SocketNativePresenter presenter);
        }

        @Module
        class ProvidersModule {
            @Provides
            NativeSocket nativeSocket(ObjectMapper mapper, NetworkConfig config) {
                return new NativeSocket(new NativeProtocol(mapper), config);
            }
        }
    }

    @ContributesAndroidInjector(modules = {CommonModule.class, NettyModule.class})
    SocketNettyActivity activity();

    @Module(includes = {
            NettyModule.BindModule.class,
            NettyModule.ProvidersModule.class})
    interface NettyModule {

        @Module
        interface BindModule {
            @Binds
            NativeView view(final SocketNettyActivity activity);

            @Binds
            NativePresenter presenter(final SocketNettyPresenter presenter);
        }

        @Module
        class ProvidersModule {
            @Provides
            NettySocket nettySocket(ObjectMapper mapper, NetworkConfig config) {
                return new NettySocket(config, new NettyProtocol(mapper));
            }
        }
    }
}
