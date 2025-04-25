package cn.tofucat.protocol.cmpp.cmpp2;

import io.netty.buffer.ByteBuf;
import cn.tofucat.protocol.cmpp.Message;

/**
 * @author zzzxb
 * 2024/11/15
 */
public interface CtxProcess {

    ByteBuf req(Message message);

    void resp(ByteBuf buf);
}
