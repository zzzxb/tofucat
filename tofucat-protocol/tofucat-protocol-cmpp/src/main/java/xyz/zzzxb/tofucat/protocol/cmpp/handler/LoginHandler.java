package xyz.zzzxb.tofucat.protocol.cmpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.zzzxb.tofucat.common.algorithm.EncryptBase;
import xyz.zzzxb.tofucat.common.utils.CipherUtils;
import xyz.zzzxb.tofucat.common.utils.DateUtils;
import xyz.zzzxb.tofucat.protocol.cmpp.HeaderInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.Message;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.CtxProcess;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.cmpp2.ServiceType;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.LoginStatus;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;


/**
 * zzzxb
 * 2023/10/16
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Message message;
    CtxProcess process;

    public LoginHandler(Message message) {
        this.message = message;
        process = ServiceFactory.getService(ServiceType.LOGIN);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buf = process.req(message);
        ctx.writeAndFlush(Unpooled.copiedBuffer(buf));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = ((ByteBuf) msg).copy();
        // 请求头 12 字节 - 丢弃不检查了
        HeaderInfo headerInfo = TCPUtils.readHeader(buf);
        if (CommandId.CMPP_CONNECT_RESP.eqCommand(headerInfo.getCommandId())) {
            process.resp(buf);
            ctx.fireChannelActive();
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("登录异常", cause);
        ctx.close();
    }
}
