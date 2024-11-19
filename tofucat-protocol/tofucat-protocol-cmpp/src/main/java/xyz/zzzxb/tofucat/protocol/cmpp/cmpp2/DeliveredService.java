package xyz.zzzxb.tofucat.protocol.cmpp.cmpp2;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.Message;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

/**
 * @author zzzxb
 * 2024/11/15
 */
public class DeliveredService implements CtxProcess {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public ByteBuf req(Message message) {
        return null;
    }

    @Override
    public void resp(ByteBuf buf) {
        long msgId = buf.readLong();
        String destId = TCPUtils.readString(buf, 21);
        buf.skipBytes(12);
        byte msgFmt = buf.readByte();
        buf.skipBytes(21);
        byte registeredDelivery = buf.readByte();
        byte msgLength = buf.readByte();
        ByteBuf msgContent = buf.readBytes(msgLength);
        buf.skipBytes(8);
        if (registeredDelivery == 1) {
            readContent(msgContent);
        }
    }

    private void readContent(ByteBuf buf) {
        String msgId = TCPUtils.formatMsgId(buf.readLong());
        String state = TCPUtils.readString(buf, 7);
        String submitTime = TCPUtils.readString(buf, 10);
        String doneTime = TCPUtils.readString(buf, 10);
        String DestTerminalId = TCPUtils.readString(buf, 10);
        Integer seq = buf.readInt();
        log.info("send report -> msgId: {}, phone:{}, status: {}, submitTime: {}", msgId, DestTerminalId, state, submitTime);
    }
}
