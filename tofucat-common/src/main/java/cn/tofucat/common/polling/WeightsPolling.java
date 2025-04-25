package cn.tofucat.common.polling;

import java.util.List;

/**
 * zzzxb
 * 2023/8/24
 */
public final class WeightsPolling<T> {
    private final List<WeightsInfo<T>> weightsInfos;
    private WeightsInfo<T> maxWeightsInfo;
    private int maxWeights;
    private int totalWeights;

    public WeightsPolling(List<WeightsInfo<T>> weightsInfos) {
        this.weightsInfos = weightsInfos;
        init();
    }

    private void init() {
        weightsInfos.forEach(info -> totalWeights += info.getOriginalWeights());
    }

    public WeightsInfo<T> polling() {
        weightsInfos.forEach(info -> {
            maxWeightInfo(info);
            info.setCurrentWeights(info.getCurrentWeights() + info.getOriginalWeights());
        });
        if (maxWeightsInfo != null) {
            maxWeightsInfo.setCurrentWeights(maxWeightsInfo.getCurrentWeights() - totalWeights);
        }
        maxWeights = 0;
        return maxWeightsInfo;
    }

    private void maxWeightInfo(WeightsInfo<T> weightsInfo) {
        if (weightsInfo.getCurrentWeights() > maxWeights) {
            maxWeights = weightsInfo.getCurrentWeights();
            maxWeightsInfo = weightsInfo;
        }
    }
}
