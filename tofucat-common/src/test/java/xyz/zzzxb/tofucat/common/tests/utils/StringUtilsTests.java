package xyz.zzzxb.tofucat.common.tests.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.utils.StringUtils;

/**
 * @author Zzzxb
 */
public class StringUtilsTests {

    @Test
    public void spare() {
        Assertions.assertEquals("a", StringUtils.spare("a", "b"));
        Assertions.assertEquals("b", StringUtils.spare("", "b"));
        Assertions.assertEquals("b", StringUtils.spare(null, "b"));
        Assertions.assertEquals("b", StringUtils.spare("\n", "b"));
    }

    @Test
    public void isBlank() {
        Assertions.assertTrue(StringUtils.isBlank(null));
        Assertions.assertTrue(StringUtils.isBlank(""));
        Assertions.assertTrue(StringUtils.isBlank(" "));
        Assertions.assertTrue(StringUtils.isBlank("  "));
        Assertions.assertTrue(StringUtils.isBlank("\t"));
        Assertions.assertTrue(StringUtils.isBlank("\n"));
        Assertions.assertTrue(StringUtils.isBlank("\r"));
        Assertions.assertTrue(StringUtils.isBlank("\t\r"));
        Assertions.assertTrue(StringUtils.isBlank("\n\r"));
        Assertions.assertTrue(StringUtils.isBlank("\t\n\r"));
        Assertions.assertTrue(StringUtils.isBlank("\t\n\r\t"));
    }

    @Test
    public void format() {
        Assertions.assertEquals("dn", StringUtils.format("d{}n{}{}"));
        Assertions.assertEquals("donkey", StringUtils.format("d{}n{}{}","o", "k", "ey"));
    }
}
