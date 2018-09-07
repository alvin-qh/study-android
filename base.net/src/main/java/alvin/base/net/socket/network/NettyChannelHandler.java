package alvin.base.net.socket.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelHandler extends ChannelInitializer<SocketChannel> {

    private final FutureCallback callback;

    public NettyChannelHandler(FutureCallback callback) {
        this.callback = callback;
    }

    private final class InboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);

            ctx.channel().close();
            callback.onDisconnected(null);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            callback.onDisconnected(cause);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
            callback.onCommandReceived(msg);
        }
    }

    public interface FutureCallback {
        void onDisconnected(Throwable err);

        void onCommandReceived(ByteBuf msg);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
//        SslContext sslCtx = SslContextBuilder.forClient()
//                .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                .build();

        ChannelPipeline pipeline = ch.pipeline();
        pipeline
//                .addLast(sslCtx.newHandler(ch.alloc()))
//                .addLast(new LoggingHandler(LogLevel.INFO))
//                .addLast(new IdleStateHandler(30, 60, 100))
//                .addLast(new MessageToMessageDecoder<String>() {
//                    @Override
//                    protected void decode(ChannelHandlerContext ctx,
//                                          String msg,
//                                          List<Object> out) throws Exception {
//                    }
//                })
//                .addLast(new MessageToMessageEncoder<String>() {
//                    @Override
//                    protected void encode(ChannelHandlerContext ctx,
//                                          String msg,
//                                          List<Object> out) throws Exception {
//                    }
//                })
                .addLast(new InboundHandler());

    }
}
