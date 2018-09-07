package alvin.base.net.socket.network;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import alvin.base.net.socket.common.commands.Command;
import alvin.base.net.socket.common.commands.CommandAck;
import alvin.base.net.socket.common.config.NetworkConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettySocket implements Closeable, AutoCloseable {
    private static final int TIMEOUT = 1000 * 5;

    private final AtomicReference<ChannelContext> contextRef = new AtomicReference<>();
    private final NetworkConfig config;
    private final EventLoopGroup group;
    private final NettyProtocol protocol;
    private final AtomicBoolean closed = new AtomicBoolean(true);

    private Callback callback;

    public interface Callback {
        void onReceived(CommandAck ack);

        void onException(NettyException err);

        void onDisconnect();

        void onConnected();
    }

    private final class ChannelContext {
        private final Channel channel;
        private ByteBuf buffer = Unpooled.EMPTY_BUFFER;

        private ChannelContext(Channel channel) {
            this.channel = channel;
        }

        void read(ByteBuf buffer, Consumer<CommandAck> onSuccess, Consumer<Throwable> onError) {
            this.buffer = Unpooled.copiedBuffer(this.buffer, buffer);
            this.buffer = protocol.unpack(this.buffer, onSuccess, onError);
        }

        boolean isClosed() {
            return !this.channel.isOpen();
        }

        void close() {
            this.channel.close();
        }
    }

    public NettySocket(NetworkConfig config, NettyProtocol protocol) {
        this.config = config;
        this.protocol = protocol;

        this.group = new NioEventLoopGroup();
    }

    public void connect(Callback callback) {
        if (closed.compareAndSet(true, false)) {
            new Bootstrap()
                    .group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyChannelHandler(new NettyChannelHandler.FutureCallback() {
                        @Override
                        public void onDisconnected(Throwable err) {
                            if (!closed.get()) {
                                reconnect(callback);
                            }
                            if (err == null) {
                                callback.onDisconnect();
                            } else {
                                callback.onException(new NettyException(err));
                            }
                        }

                        @Override
                        public void onCommandReceived(ByteBuf msg) {
                            final ChannelContext context = contextRef.get();
                            context.read(msg,
                                    commandAck -> onMessageReceived(commandAck, callback),
                                    throwable -> {
                                        if (!closed.get()) {
                                            callback.onException(new NettyException(throwable));
                                            reconnect(callback);
                                        }
                                    });
                        }
                    }))
                    .connect(config.getHost(), config.getPort())
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            contextRef.set(new ChannelContext(future.channel()));
                            callback.onConnected();
                        } else {
                            callback.onException(new NettyException("Cannot connect to remote"));
                        }
                    });
            this.callback = callback;
        }
    }

    private void onMessageReceived(CommandAck ack, Callback callback) {
        switch (ack.getCmd()) {
        case "time-ack":
        case "bye-ack":
            callback.onReceived(ack);
            break;
        default:
            close();
            callback.onException(new NettyException("Invalid command"));
        }
    }

    public void getRemoteTime(Consumer<Command> callback) {
        sendPackage(new Command("time"), callback);
    }

    public void disconnect(Consumer<Command> callback) {
        sendPackage(new Command("bye"), callback);
    }

    private void sendPackage(final Command cmd, Consumer<Command> callback) {
        final ChannelContext context = contextRef.get();
        if (context != null) {
            final ByteBuf msg = protocol.makeCommandPackage(cmd);
            context.channel.writeAndFlush(msg).addListener(future -> {
                if (future.isSuccess()) {
                    callback.accept(cmd);
                } else {
                    reconnect(this.callback);
                }
            });
        }
    }

    private void reconnect(Callback callback) {
        final ChannelContext context = contextRef.getAndSet(null);
        if (context != null) {
            context.channel.close();
        }
        connect(callback);
    }

    public boolean isClose() {
        return closed.get();
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            final ChannelContext context = contextRef.getAndSet(null);
            if (context != null) {
                context.channel.close();
            }
        }
    }
}
