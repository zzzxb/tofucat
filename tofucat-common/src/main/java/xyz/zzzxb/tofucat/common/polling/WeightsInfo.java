package xyz.zzzxb.tofucat.common.polling;

/**
 * zzzxb
 * 2023/8/24
 * <a href="http://zzzxb.xyz/2023/08/24/平滑路由/">平滑分权路由</a>
 */
public class WeightsInfo<T> {
    /**
     * 原始权重
     */
    private int originalWeights;
    /**
     * 当前权重
     */
    private int currentWeights;
    private T data;

    public WeightsInfo(T data, int originalWeights) {
        this.originalWeights = originalWeights;
        this.currentWeights = originalWeights;
        this.data = data;
    }

    public int getCurrentWeights() {
        return currentWeights;
    }

    public void setCurrentWeights(int currentWeights) {
        this.currentWeights = currentWeights;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getOriginalWeights() {
        return originalWeights;
    }

    public void setOriginalWeights(int originalWeights) {
        this.originalWeights = originalWeights;
    }

    @Override
    public String toString() {
        return "WeightsInfo{" +
                "currentWeights=" + currentWeights +
                ", originalWeights=" + originalWeights +
                ", data=" + data +
                '}';
    }
}