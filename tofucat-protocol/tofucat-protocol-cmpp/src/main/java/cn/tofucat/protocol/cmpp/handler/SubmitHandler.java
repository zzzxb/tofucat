package cn.tofucat.protocol.cmpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.tofucat.protocol.cmpp.HeaderInfo;
import cn.tofucat.protocol.cmpp.Message;
import cn.tofucat.protocol.cmpp.cmpp2.CtxProcess;
import cn.tofucat.protocol.cmpp.cmpp2.ServiceFactory;
import cn.tofucat.protocol.cmpp.cmpp2.ServiceType;
import cn.tofucat.protocol.cmpp.enums.CommandId;
import cn.tofucat.protocol.cmpp.util.TCPUtils;

/**
 * zzzxb
 * 2023/10/31
 */
public class SubmitHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Message message;
    private final CtxProcess process;

    public SubmitHandler(Message message) {
        this.message = message;
        process = ServiceFactory.getService(ServiceType.SUBMIT);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = process.req(message);
        ctx.writeAndFlush((Unpooled.copiedBuffer(buf)));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = ((ByteBuf) msg).copy();
        HeaderInfo headerInfo = TCPUtils.readHeader(buf);
        if (CommandId.CMPP_SUBMIT_RESP.eqCommand(headerInfo.getCommandId())) {
            process.resp(buf);
            ctx.fireChannelActive();
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("短信提交异常", cause);
        ctx.close();
    }
}
