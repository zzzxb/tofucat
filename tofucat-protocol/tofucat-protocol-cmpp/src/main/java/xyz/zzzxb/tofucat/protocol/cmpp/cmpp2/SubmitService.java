package xyz.zzzxb.tofucat.protocol.cmpp.cmpp2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.Message;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.SubmitStatus;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

import java.nio.charset.Charset;

/**
 * @author zzzxb
 * 2024/11/15
 */
public class SubmitService implements CtxProcess {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public ByteBuf req(Message message) {
        int contentByteLen = message.getContent().getBytes(Charset.forName("GBK")).length;
        int phoneByteLen = message.getPhones().size() * 21;

        ByteBuf buf = Unpooled.buffer();
        // 请求头 4 字节 - 消息总长度
        buf.writeInt(138 + contentByteLen + phoneByteLen);
        // 请求头 4 字节 - 命令或响应类型
        buf.writeInt(CommandId.CMPP_SUBMIT.getCode());
        // 请求头 4 字节 - 消息流水号
        buf.writeInt(0);
        // msgId, 本处填空
        buf.writeLong(0);
        // 消息总条数 从1开始
        buf.writeByte(1);
        // 消息序号
        buf.writeByte(1);
        // 是否需要状态报告 0不需要 1需要 2 产生SMC话单
        buf.writeByte(1);
        // 信息级别
        buf.writeZero(1);
        // 业务类型 数字字母符号组合
        buf.writeZero(10);
        // 计费用户类型字段
        buf.writeByte(3);
        // 被计费用户的号码
        buf.writeZero(21);
        // GSM 协议类型
        buf.writeZero(1);
        // GSM 协议类型
        buf.writeZero(1);
        // 信息格式 15GBK 汉字格式
        buf.writeByte(15);
        // 消息体 6 字节 - SP_ID
        TCPUtils.fixedLength(buf, message.getAccountInfo().getSpId(), 6);
        //  资费类别
        buf.writeZero(42);
        TCPUtils.fixedLength(buf, "10693682", 21);
        // 接收信息的用户数量 < 100
        buf.writeByte(message.getPhones().size());
        for (String phone : message.getPhones()) {
            TCPUtils.fixedLength(buf, phone, 21);
            log.info("submit req -> phone: {} content: {}", phone, message.getContent());
        }
        buf.writeByte(contentByteLen);
        TCPUtils.writeString(buf, message.getContent());
        buf.writeZero(8);
        return buf;
    }

    @Override
    public void resp(ByteBuf buf) {
        String msgId = TCPUtils.formatMsgId(buf.readLong());
        byte result = buf.readByte();
        String desc = SubmitStatus.getDesc(result);
        log.info("submit resp -> msgId: {} result: {} desc: {}", msgId, result, desc);
    }
}
