package xyz.zzzxb.protocol.cmpp.tests;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.protocol.cmpp.AccountInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.Message;
import xyz.zzzxb.tofucat.protocol.cmpp.handler.ChannelCMPP;

/**
 * @author zzzxb
 * 2024/9/18
 */
public class SendMsgTests {

    @Test
    public void test() {
        Message message = new Message()
                .setAccountInfo(new AccountInfo()
                        .address("localhost", 27001)
                        .account("testms", "3l6v72o"))
                .setPhone("12345678910")
                .setContent("85304055");
        send(message);
    }

    private void send(Message message) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
//        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 6);
        b.handler(new ChannelCMPP(message));

        try {
            ChannelFuture f = b.connect(message.getAccountInfo().getHost(), message.getAccountInfo().getPort()).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
