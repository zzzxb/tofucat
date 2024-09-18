package xyz.zzzxb.tofucat.common.tests.polling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.polling.WeightsInfo;
import xyz.zzzxb.tofucat.common.polling.WeightsPolling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * zzzxb
 * 2024/9/13
 */
public class WeightsPollingTests {

    @Test
    public void polling() {
        // 创建需要路由器的数据以及分配的权重
        List<WeightsInfo<String>> weightsInfoList = new LinkedList<>();
        weightsInfoList.add(new WeightsInfo<>("a", 10));
        weightsInfoList.add(new WeightsInfo<>("b", 30));
        weightsInfoList.add(new WeightsInfo<>("c", 60));
        WeightsPolling<String> weightsPolling = new WeightsPolling<>(weightsInfoList);

        AtomicInteger totalWeights = new AtomicInteger();
        weightsInfoList.forEach(info -> totalWeights.addAndGet(info.getOriginalWeights()));

        Map<String, Integer> recordMap = new HashMap<>();
        for (int i = 0; i < totalWeights.get(); i++) {
            WeightsInfo<String> polling = weightsPolling.polling();
            recordMap.compute(polling.getData(), (k, v) -> v == null ? 1 : v + 1);
        }

        weightsInfoList.forEach(info -> {
            Assertions.assertEquals(recordMap.get(info.getData()).intValue(), info.getOriginalWeights());
        });
    }
}
