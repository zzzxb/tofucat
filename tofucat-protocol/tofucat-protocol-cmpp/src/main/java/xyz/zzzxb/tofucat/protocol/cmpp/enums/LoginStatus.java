package xyz.zzzxb.tofucat.protocol.cmpp.enums;


/**
 * zzzxb
 * 2023/8/29
 */
public enum LoginStatus {
    SUCCESS(0, "登录成功"),
    MESSAGE_STRUCT_FAIL(1, "消息结构错"),
    IP_SOURCE_FAIL(2, "非法源原地址"),
    AUTHORIZATION(3, "认证错误"),
    VERSION_FAIL(4, "版本太高"),
    OTHER_FAIL(5, "其他错误"),
    ;

    private final Integer code;
    private final String desc;

    LoginStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int code) {
        return switch (code) {
            case 0 -> SUCCESS.desc;
            case 1 -> MESSAGE_STRUCT_FAIL.desc;
            case 2 -> IP_SOURCE_FAIL.desc;
            case 3 -> AUTHORIZATION.desc;
            case 4 -> VERSION_FAIL.desc;
            default -> OTHER_FAIL.desc;
        };
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
