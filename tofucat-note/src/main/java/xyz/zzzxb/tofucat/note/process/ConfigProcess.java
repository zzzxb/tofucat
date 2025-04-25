package xyz.zzzxb.tofucat.note.process;

import cn.tofucat.common.utils.DateUtils;
import cn.tofucat.common.utils.FileUtils;
import cn.tofucat.common.utils.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zzzxb
 * 2025/4/25
 */
public class ConfigProcess extends ProcessAbstract {

    @Override
    public void init() {
        String homeDir = StringUtils.spare(System.getenv("TF_Note_RootDir"), System.getenv("HOME"));
        String configFile = FileUtils.joinPath(homeDir, ".tofucat", "note", ".config");
        Map<String, String> configMap = new HashMap<>();
        configMap.put("CreateTime", DateUtils.formatNowDate("yyyy-MM-dd hh:mm:ss"));
        FileUtils.canCreateFile(configFile, new Gson().toJson(configMap));
    }
}
