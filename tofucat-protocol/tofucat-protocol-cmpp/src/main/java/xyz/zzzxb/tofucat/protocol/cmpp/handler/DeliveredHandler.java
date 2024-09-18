package xyz.zzzxb.tofucat.protocol.cmpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.zzzxb.tofucat.protocol.cmpp.HeaderInfo;
import xyz.zzzxb.tofucat.protocol.cmpp.enums.CommandId;
import xyz.zzzxb.tofucat.protocol.cmpp.util.TCPUtils;

/**
 * zzzxb
 * 2023/11/8
 */
public class DeliveredHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = ((ByteBuf) msg).copy();
        HeaderInfo headerInfo = TCPUtils.readHeader(buf);
        if (CommandId.CMPP_DELIVER.eqCommand(headerInfo.getCommandId())) {
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
            ctx.close();
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("短信回执异常", cause);
        super.exceptionCaught(ctx, cause);
    }
}
