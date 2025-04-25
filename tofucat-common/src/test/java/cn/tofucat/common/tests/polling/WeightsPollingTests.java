package cn.tofucat.common.tests.polling;

import org.junit.jupiter.api.Test;
import cn.tofucat.common.polling.WeightsInfo;
import cn.tofucat.common.polling.WeightsPolling;

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

        // 用于控制数据总量，也可可直接指定
        AtomicInteger totalWeights = new AtomicInteger();
        weightsInfoList.forEach(info -> totalWeights.addAndGet(info.getOriginalWeights()));

        // 用来记录最后数据，观察权重是否分配正确
        Map<String, Integer> recordMap = new HashMap<>();
        for (int i = 0; i < totalWeights.get(); i++) {
            WeightsInfo<String> polling = weightsPolling.polling();
            recordMap.compute(polling.getData(), (k, v) -> v == null ? 1 : v + 1);
        }

        // 检查最后结果是否跟权重匹配
//        weightsInfoList.forEach(info -> {
//            Assertions.assertEquals(recordMap.get(info.getData()).intValue(), info.getOriginalWeights());
//        });

        System.out.println("配置的权重: " + weightsInfoList);
        System.out.println("路由后结果: " + recordMap);
    }
}
