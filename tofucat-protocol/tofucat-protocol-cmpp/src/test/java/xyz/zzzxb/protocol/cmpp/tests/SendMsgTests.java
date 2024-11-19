package xyz.zzzxb.protocol.cmpp.tests;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.protocol.cmpp.AccountInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.HeaderInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.Message;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.CtxProcess;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceType;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.handler.ChannelCMPP;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

/**
 * @author zzzxb
 * 2024/9/18
 */
public class SendMsgTests {
    boolean login = false;

    @Test
    public void test() {
        Message message = new Message()
                .setAccountInfo(new AccountInfo()
                        .address("localhost", 27001)
                        .account("testms", "3l6v72o"))
                .setPhone("12345678910")
                .setContent("85304055");
        once(message);
    }

    private void once(Message message) {
        createCollection(message, new ChannelCMPP(message));
    }

    private void createCollection(Message message, ChannelHandler channelHandler) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
//        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 6);
        b.handler(channelHandler);

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
