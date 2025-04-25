package cn.tofucat.common.cache;

/**
 * @author zzzxb
 * 2024/10/15
 */
public interface Cache<T> {
    void add(String key, T value);

    void add(String key, T value, long expire);

    T get(String key);

    boolean expire(String key, long exp);

    boolean remove(String key);
}
