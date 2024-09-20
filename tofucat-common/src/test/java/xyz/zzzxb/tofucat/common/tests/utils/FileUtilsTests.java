package xyz.zzzxb.tofucat.common.tests.utils;

import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.utils.FileUtils;

/**
 * @author zzzxb
 * 2024/9/20
 */
public class FileUtilsTests {

    @Test
    public void readInternal() {
        String s = FileUtils.readInternal("/op.json");
        System.out.println(s);
    }
}
