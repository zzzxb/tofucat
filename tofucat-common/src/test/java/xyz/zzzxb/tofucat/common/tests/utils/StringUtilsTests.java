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
        Assertions.assertEquals(StringUtils.spare("a", "b"), "a");
        Assertions.assertEquals(StringUtils.spare("", "b"), "b");
        Assertions.assertEquals(StringUtils.spare(null, "b"), "b");
        Assertions.assertEquals(StringUtils.spare("\n", "b"), "b");
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
        Assertions.assertEquals(StringUtils.format("d{}n{}{}","o", "k", "ey"), "donkey");
    }
}
