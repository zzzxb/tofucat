package xyz.zzzxb.tofucat.common.tests.op;

import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.op.Operator;

/**
 * @author zzzxb
 * 2024/9/20
 */
public class OpTests {

    @Test
    public void load() {
        Operator operator = new Operator();
        operator.load();
    }
}
