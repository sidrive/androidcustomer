package lawyerku.customer.preference;

public class GlobalPreference {
    private static final String CACHE_NAME = GlobalPreference.class.getName();

    private static CachePreferences cachePreferences;

    private static CachePreferences getInstance() {
        if (cachePreferences == null)
            cachePreferences = new CachePreferences(CACHE_NAME);
        return cachePreferences;
    }

    public static synchronized <T> T read(String key, Class<T> tClass) {
        return getInstance().read(key, tClass);
    }

    public static synchronized <T> void write(String key, T value, Class<T> tClass) {
        getInstance().write(key, value, tClass);
    }

    public static synchronized void clear() {
        getInstance().clear();
    }
}
