package xyz.zzzxb.tofucat.protocol.cmpp;


/**
 * zzzxb
 * 2023/11/8
 */
public class HeaderInfo {
    /**
     * 消息总长度(含消息头及消息体)
     */
     Integer totalLength;
    /**
     * 命令或响应类型
     */
    private Integer commandId;
    /**
     * 消息流水号,顺序累加,步长为 1,循环使用 (一对请求和应答消息的流水号必须相 同
     */
    private Integer sequenceId;

    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Integer totalLength) {
        this.totalLength = totalLength;
    }
}
