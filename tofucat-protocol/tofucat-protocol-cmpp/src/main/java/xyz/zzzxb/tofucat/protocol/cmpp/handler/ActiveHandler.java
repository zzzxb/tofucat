package xyz.zzzxb.tofucat.protocol.cmpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import xyz.zzzxb.tofucat.protocol.cmpp.HeaderInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.CtxProcess;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceType;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zzzxb
 * 2024/11/19
 */
public class ActiveHandler extends ChannelInboundHandlerAdapter {
    private final CtxProcess process;
    private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private static boolean startup = false;

    public ActiveHandler() {
        process = ServiceFactory.getService(ServiceType.ACTIVE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(!startup) {
            startup = true;
            service.scheduleAtFixedRate(() -> {
                ByteBuf buf = process.req(null);
                ctx.writeAndFlush(Unpooled.copiedBuffer(buf));
            }, 0, 60, TimeUnit.SECONDS);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = ((ByteBuf) msg).copy();
        HeaderInfo headerInfo = TCPUtils.readHeader(buf);
        if(CommandId.CMPP_ACTIVE_TEST_RESP.eqCommand(headerInfo.getCommandId())) {
            process.resp(buf);
        }
    }
}
