package alvin.base.net.socket.netty.net;

import android.support.annotation.NonNull;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import alvin.base.net.socket.common.config.NetworkConfig;
import alvin.base.net.socket.common.models.Command;
import alvin.base.net.socket.common.models.CommandAck;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SocketNetty implements Closeable, AutoCloseable {

    private static final int TIMEOUT = 1000 * 5;
    private final EventLoopGroup group;

    private final Map<Channel, ChannelContextImpl> contextMap = new ConcurrentHashMap<>();

    private final OnNetworkFutureListener listener;

    public SocketNetty(OnNetworkFutureListener listener) {
        this.listener = listener;
        this.group = new NioEventLoopGroup();

        NetworkConfig config = new NetworkConfig();
        Bootstrap bootstrap = new Bootstrap().group(group)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                      SslContext sslCtx = SslContextBuilder.forClient()
//                              .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                              .build();

                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline
//                              .addLast(sslCtx.newHandler(ch.alloc()))
//                              .addLast(new LoggingHandler(LogLevel.INFO))
//                              .addLast(new IdleStateHandler(30, 60, 100))
//                              .addLast(new MessageToMessageDecoder<String>() {
//                                  @Override
//                                  protected void decode(ChannelHandlerContext ctx,
//                                                        String msg,
//                                                        List<Object> out) throws Exception {
//                                  }
//                              })
//                              .addLast(new MessageToMessageEncoder<String>() {
//                                  @Override
//                                  protected void encode(ChannelHandlerContext ctx,
//                                                        String msg,
//                                                        List<Object> out) throws Exception {
//                                  }
//                              })
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    public void channelInactive(ChannelHandlerContext ctx)
                                            throws Exception {

                                        super.channelInactive(ctx);

                                        final Channel channel = ctx.channel();
                                        ChannelContextImpl context = contextMap.remove(channel);
                                        channel.close();

                                        listener.onDisconnected(context);
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx,
                                                                ByteBuf msg) throws Exception {
                                        ChannelContextImpl c = contextMap.get(ctx.channel());
                                        if (c == null) {
                                            ctx.disconnect().addListener(
                                                    (ChannelFutureListener) future ->
                                                            future.channel().disconnect());
                                            return;
                                        }
                                        c.read(msg, ack -> onMessageReceived(c, ack),
                                                t -> ctx.disconnect());
                                    }
                                });
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        super.channelInactive(ctx);

                        Channel channel = ctx.channel();
                        contextMap.remove(channel);
                        channel.close();
                    }
                });

        bootstrap.connect(config.getHost(), config.getPort())
                .addListener((ChannelFutureListener) future -> {
                    final ChannelContextImpl context = new ChannelContextImpl();
                    if (future.isSuccess()) {
                        contextMap.put(future.channel(), context);
                        listener.onConnected(context);
                    } else {
                        listener.onError(context,
                                new SocketNetworkException(SocketErrorStatus.CONNECT));
                    }
                });
    }

    private void onMessageReceived(ChannelContextImpl context, CommandAck ack) {
        switch (ack.getCmd()) {
        case "time-ack":
        case "bye-ack":
            this.listener.onCommandReceived(context, ack);
            break;
        default:
            throw new SocketNetworkException(SocketErrorStatus.READ, "Invalid command");
        }
    }

    public void getRemoteTime() {
        sendPackage(new Command("time"));
    }

    public void disconnect() {
        sendPackage(new Command("bye"));
    }

    private void sendPackage(final Command cmd) {
        final ByteBuf buffer = NettyProtocol.makeCommandPackage(cmd);

        for (Map.Entry<Channel, ChannelContextImpl> entry : contextMap.entrySet()) {
            entry.getKey().writeAndFlush(buffer.copy())
                    .addListener(future -> listener.onCommandSent(entry.getValue(), cmd));
        }
    }

    @Override
    public void close() {
        for (Channel channel : contextMap.keySet()) {
            if (channel.isOpen() && channel.isActive()) {
                channel.disconnect();
            }
        }
        contextMap.clear();
        group.shutdownGracefully();
    }

    private static final class ChannelContextImpl implements ChannelContext {

        private ByteBuf buffer = Unpooled.EMPTY_BUFFER;
        private NettyProtocol protocol = new NettyProtocol();

        void read(@NonNull ByteBuf buffer,
                  Consumer<CommandAck> onSuccess,
                  Consumer<Throwable> onError) {
            this.buffer = Unpooled.copiedBuffer(this.buffer, buffer);
            this.buffer = this.protocol.unpack(this.buffer, onSuccess, onError);
        }
    }

    public interface OnNetworkFutureListener {
        void onConnected(ChannelContext context);

        void onDisconnected(ChannelContext context);

        void onCommandReceived(ChannelContext context, CommandAck cmd);

        void onCommandSent(ChannelContext context, Command cmd);

        void onError(ChannelContext context, Throwable t);
    }
}
