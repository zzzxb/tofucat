package xyz.zzzxb.tofucat.protocol.cmpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.HeaderInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.CtxProcess;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceType;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

/**
 * zzzxb
 * 2023/11/8
 */
public class DeliveredHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final CtxProcess process;

    public DeliveredHandler() {
        process = ServiceFactory.getService(ServiceType.DELIVERED);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = ((ByteBuf) msg).copy();
        HeaderInfo headerInfo = TCPUtils.readHeader(buf);
        if (CommandId.CMPP_DELIVER.eqCommand(headerInfo.getCommandId())) {
            process.resp(buf);
            ctx.close();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("短信回执异常", cause);
        super.exceptionCaught(ctx, cause);
    }
}
