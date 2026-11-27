import java.util.LinkedHashMap;
import java.util.Map;

public class Task13 {
    interface CachePolicyStrategy {
        String selectKeyToEvict(Map<String, String> cache);
    }

    static class LruEvictionStrategy implements CachePolicyStrategy {
        @Override
        public String selectKeyToEvict(Map<String, String> cache) {
            String key = cache.keySet().iterator().next();
            System.out.println("LruEvictionStrategy: evicting least recently used key " + key);
            return key;
        }
    }

    static class FifoEvictionStrategy implements CachePolicyStrategy {
        @Override
        public String selectKeyToEvict(Map<String, String> cache) {
            String key = cache.keySet().iterator().next();
            System.out.println("FifoEvictionStrategy: evicting oldest key " + key);
            return key;
        }
    }

    static class TtlExpirationStrategy implements CachePolicyStrategy {
        @Override
        public String selectKeyToEvict(Map<String, String> cache) {
            String key = cache.keySet().iterator().next();
            System.out.println("TtlExpirationStrategy: expiring TTL key " + key);
            return key;
        }
    }

    static class QuerySanitizer {
        public String sanitize(String sql, String params) {
            return sql.trim() + " | params=" + params;
        }
    }

    static class MemoryCacheStore {
        private final Map<String, String> cache = new LinkedHashMap<>();
        private final int maxSize = 2;

        public String get(String key) {
            return cache.get(key);
        }

        public void put(String key, String value, CachePolicyStrategy policy) {
            if (cache.size() >= maxSize && !cache.containsKey(key)) {
                cache.remove(policy.selectKeyToEvict(cache));
            }
            cache.put(key, value);
        }
    }

    static class ConnectionPool {
        public String queryDatabase(String sanitizedQuery) {
            System.out.println("ConnectionPool: executing on DB -> " + sanitizedQuery);
            return "ResultSet(" + sanitizedQuery + ")";
        }
    }

    static class QueryMetricsRecorder {
        public void record(boolean cacheHit) {
            System.out.println("QueryMetricsRecorder: " + (cacheHit ? "CACHE_HIT" : "CACHE_MISS"));
        }
    }

    static class DataAccessFacade {
        private final QuerySanitizer sanitizer = new QuerySanitizer();
        private final MemoryCacheStore cacheStore = new MemoryCacheStore();
        private final ConnectionPool connectionPool = new ConnectionPool();
        private final QueryMetricsRecorder metricsRecorder = new QueryMetricsRecorder();
        private CachePolicyStrategy strategy;

        public DataAccessFacade(CachePolicyStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(CachePolicyStrategy strategy) {
            this.strategy = strategy;
        }

        public String executeQuery(String sql, String params) {
            String key = sanitizer.sanitize(sql, params);
            String cached = cacheStore.get(key);
            if (cached != null) {
                metricsRecorder.record(true);
                return cached;
            }
            metricsRecorder.record(false);
            String result = connectionPool.queryDatabase(key);
            cacheStore.put(key, result, strategy);
            return result;
        }
    }

    public static void main(String[] args) {
        DataAccessFacade dao = new DataAccessFacade(new LruEvictionStrategy());
        dao.executeQuery("SELECT * FROM users WHERE id=?", "1");
        dao.executeQuery("SELECT * FROM users WHERE id=?", "1");
        dao.executeQuery("SELECT * FROM users WHERE id=?", "2");
        dao.setStrategy(new TtlExpirationStrategy());
        dao.executeQuery("SELECT * FROM users WHERE id=?", "3");
    }
}
