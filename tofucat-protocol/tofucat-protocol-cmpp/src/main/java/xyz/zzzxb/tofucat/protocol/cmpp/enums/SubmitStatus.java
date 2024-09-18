package xyz.zzzxb.tofucat.protocol.cmpp.enums;


/**
 * zzzxb
 * 2023/8/30
 */
public enum SubmitStatus {
    SUCCESS(0, "提交成功"),
    MESSAGE_STRUCT_FAIL(1, "消息结构错"),
    COMMAND_FAIL(2, "命令字错误"),
    MESSAGE_SEQ_FAIL(3, "消息序号错误"),
    MESSAGE_LEN_FAIL(4, "消息长度错误"),
    FEE_CODE_FAIL(5, "资费代码错误"),
    MESSAGE_TOO_LONG(6, "超过最大信息长"),
    BUSINESS_CODE_FAIL(7, "业务代码错误"),
    RATE_CTRL_FAIL(8, "流量控制错误"),
    GW_NO_BILLING(9, "本网关不负责服务此计费号码"),
    SRC_ID_FAIL(10, "SRC_ID 错误"),
    MSG_SRC_FAIL(11, "MSG_SRC 错误"),
    FEE_TERMINAL_ID_FAIL(12, "FEE_TERMINAL_ID 错误"),
    DEST_TERMINAL_ID_FAIL(13, "DEST_TERMINAL_ID 错误");
    private final int code;
    private final String desc;

    SubmitStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDesc(int code) {
        return switch (code) {
            case 0 -> SUCCESS.desc;
            case 1 -> MESSAGE_STRUCT_FAIL.desc;
            case 2 -> COMMAND_FAIL.desc;
            case 3 -> MESSAGE_SEQ_FAIL.desc;
            case 4 -> MESSAGE_LEN_FAIL.desc;
            case 5 -> FEE_CODE_FAIL.desc;
            case 6 -> MESSAGE_TOO_LONG.desc;
            case 7 -> BUSINESS_CODE_FAIL.desc;
            case 8 -> RATE_CTRL_FAIL.desc;
            case 9 -> GW_NO_BILLING.desc;
            case 10 -> SRC_ID_FAIL.desc;
            case 11 -> MSG_SRC_FAIL.desc;
            case 12 -> FEE_TERMINAL_ID_FAIL.desc;
            case 13 -> DEST_TERMINAL_ID_FAIL.desc;
            default -> "Unexpected value: " + code;
        };
    }
}
