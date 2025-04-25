package cn.tofucat.common.cache;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzzxb
 * 2024/10/15
 */
public class TofuCache<T> implements Cache<T> {
    private final ConcurrentHashMap<String, CacheValue<T>> CACHE_MAP;

    public TofuCache() {
        CACHE_MAP = new ConcurrentHashMap<>();
    }

    @Override
    public void add(String key, T value) {
        add(key, value, -1);
    }

    @Override
    public void add(String key, T value, long expire) {
        CACHE_MAP.put(key, new CacheValue<T>(value, expire));
    }

    @Override
    public T get(String key) {
        CacheValue<T> cv = CACHE_MAP.get(key);
        if (isExpire(cv)) {
            remove(key);
            return null;
        }
        return cv.getValue();
    }

    public boolean expire(String key, long exp) {
        CacheValue<T> cv = CACHE_MAP.get(key);
        if (cv != null) {
            cv.setExpire(exp);
            return true;
        }
        return false;
    }

    public void cleanExpire() {
        Set<Map.Entry<String, CacheValue<T>>> entries = CACHE_MAP.entrySet();
        for (Map.Entry<String, CacheValue<T>> entry : entries) {
            if(isExpire(entry.getValue())) {
                remove(entry.getKey());
            }
        }
    }

    public void cleanAll() {
        CACHE_MAP.clear();
    }

    private boolean isExpire(CacheValue<T> cv) {
        return !(cv != null && (cv.getExpire() == -1L || (cv.getTimestamp() + cv.getExpire()) - System.currentTimeMillis() > 0));
    }

    private boolean checkExpire(String key) {
        CacheValue<T> cv = CACHE_MAP.get(key);
        return isExpire(cv);
    }

    @Override
    public boolean remove(String key) {
        CacheValue<T> cv = CACHE_MAP.remove(key);
        return cv != null;
    }

    private static class CacheValue<T> {
        private T value;
        private long expire;
        private long timestamp;

        public CacheValue(T value, long expire) {
            this.value = value;
            setExpire(expire);
        }

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
            this.timestamp = System.currentTimeMillis();
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
