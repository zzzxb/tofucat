package xyz.zzzxb.tofucat.protocol.cmpp.cmpp2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.zzzxb.tofucat.common.algorithm.EncryptBase;
import xyz.zzzxb.tofucat.common.utils.CipherUtils;
import xyz.zzzxb.tofucat.common.utils.DateUtils;
import xyz.zzzxb.tofucat.protocol.cmpp.Message;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.LoginStatus;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

/**
 * @author zzzxb
 * 2024/11/15
 */
public class LoginService implements CtxProcess {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public ByteBuf req(Message message) {
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

        log.info("log req -> spId: [{}] sk: [{}] collection [{}:{}]", spId, sk,
                message.getAccountInfo().getHost(), message.getAccountInfo().getPort());
        return buf;
    }

    @Override
    public void resp(ByteBuf buf) {
        byte status = buf.readByte();
        log.info("log resp -> status: {}, desc: {}", status, LoginStatus.getDesc(status));
    }
}
