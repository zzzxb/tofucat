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

    public LoginHandler(Message message) {
        this.message = message;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String spId = message.getAccountInfo().getSpId();
        String sk = message.getAccountInfo().getSk();
        String timestamp = DateUtils.formatNowDate("MMddHHmmss");
        String auth = spId + "\0\0\0\0\0\0\0\0\0" + sk + timestamp;

        ByteBuf buf = Unpooled.buffer();
        // 请求头 4 字节 - 消息总长度
        buf.writeInt(39);
        // 请求头 4 字节 - 命令或响应类型
        buf.writeInt(CommandId.CMPP_CONNECT.getCode());
        // 请求头 4 字节 - 消息流水号
        buf.writeZero(4);
        // 消息体 6 字节 - SP_ID
        TCPUtils.fixedLength(buf, spId, 6);
        // 消息体 16 字节 - 鉴权码
        buf.writeBytes(CipherUtils.buildDigest(auth, EncryptBase.Encrypt.MD5));
        // 消息体 1 字节 - 版本号
        buf.writeByte(0x20);
        // 消息体 4 字节 - 时间戳
        buf.writeInt(Integer.parseInt(timestamp));

        ctx.writeAndFlush(buf);
        log.info("log req -> spId: [{}] sk: [{}] collection [{}:{}]", spId, sk,
                message.getAccountInfo().getHost(), message.getAccountInfo().getPort());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = ((ByteBuf) msg).copy();
        // 请求头 12 字节 - 丢弃不检查了
        HeaderInfo headerInfo = TCPUtils.readHeader(buf);
        if (CommandId.CMPP_CONNECT_RESP.eqCommand(headerInfo.getCommandId())) {
            // 消息体 4 字节 - 登录状态
            byte status = buf.readByte();

            log.info("log resp -> status: {}, desc: {}", status, LoginStatus.getDesc(status));
            if (status == LoginStatus.SUCCESS.getCode()) {
                ctx.fireChannelActive();
            } else {
                throw new RuntimeException("登录失败");
            }
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
