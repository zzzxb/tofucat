package cn.tofucat.protocol.cmpp.cmpp2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.tofucat.protocol.cmpp.Message;
import cn.tofucat.protocol.cmpp.enums.CommandId;

/**
 * @author zzzxb
 * 2024/11/15
 */
public class ActiveService implements CtxProcess {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public ByteBuf req(Message message) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(12);
        buf.writeInt(CommandId.CMPP_ACTIVE_TEST.getCode());
        buf.writeZero(4);
        log.info("[链路检测] 链路保持");
        return buf;
    }

    @Override
    public void resp(ByteBuf buf) {
        byte b = buf.readByte();
        log.info("[链路检测] Reserved: {}", b);
    }
}
