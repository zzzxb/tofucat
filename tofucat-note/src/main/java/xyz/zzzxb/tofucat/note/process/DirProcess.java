package xyz.zzzxb.tofucat.note.process;

import cn.tofucat.common.utils.FileUtils;
import cn.tofucat.common.utils.StringUtils;

/**
 *
 * @author zzzxb
 * 2025/4/25
 */
public class DirProcess extends ProcessAbstract {
    @Override
    public void init() {
        String homeDir = StringUtils.spare(System.getenv("TF_Note_RootDir"), System.getenv("HOME"));
        String rootDir = FileUtils.joinPath(homeDir, ".tofucat", "note");
        FileUtils.canCreateDirectories(rootDir);
    }
}
