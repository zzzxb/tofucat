package xyz.zzzxb.tofucat.common.op;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.zzzxb.tofucat.common.utils.CheckParamUtils;
import xyz.zzzxb.tofucat.common.utils.FileUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzzxb
 * 2024/9/20
 */
public class Operator {
    private final ConcurrentHashMap<String, OpType> OP_MAP = new ConcurrentHashMap<>();

    public void load() {
        String s = FileUtils.readInternal("/op.json");
        JsonObject jo = JsonParser.parseString(s).getAsJsonObject();
        jo.getAsJsonArray("mobile").forEach(info -> OP_MAP.put(info.getAsString(), OpType.MOBILE));
        jo.getAsJsonArray("unicom").forEach(info -> OP_MAP.put(info.getAsString(), OpType.UNICOM));
        jo.getAsJsonArray("telecom").forEach(info -> OP_MAP.put(info.getAsString(), OpType.TELCOM));
        jo.getAsJsonArray("no_op").forEach(info -> OP_MAP.put(info.getAsString(), OpType.NO_OP));
    }

    /**
     * <p>中国移动 mobile</p>
     * <p>中国联通 unicom</p>
     * <p>中国电信 telecom</p>
     * <p>中国广电 bn</p>
     * <p>无运营商 no_op <i>130 ~ 199 范围内无运营商采用</i></p>
     * <p>无效的 invalid <i>不在 130 ~ 199 范围内</i></p>
     *
     * @param phone 中国手机号或者手机号前三位
     * @return mobile, unicom, telecom, bn, no_operator, invalid
     */
    public OpType takeOp(String phone) {
        CheckParamUtils.isBlack(phone).throwMessage("手机号不能为空");
        CheckParamUtils.isTrue(phone.length() < 3).throwMessage("至少需要传入中国手机号前三位");
        String phonePrefix = phone.length() == 3 ? phone : phone.substring(0, 3);
        OpType opType = OP_MAP.get(phonePrefix);
        return opType == null ? OpType.INVALID : opType;
    }

}