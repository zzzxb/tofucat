package cn.tofucat.protocol.cmpp.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import cn.tofucat.protocol.cmpp.Message;

/**
 * zzzxb
 * 2023/10/31
 */
public class ChannelCMPP extends ChannelInitializer<SocketChannel> {
    private final Message message;

    public ChannelCMPP(Message message) {
        this.message = message;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new LoginHandler(message))
                .addLast(new SubmitHandler(message))
                .addLast(new DeliveredHandler())
                .addLast(new ActiveHandler());
    }
}
