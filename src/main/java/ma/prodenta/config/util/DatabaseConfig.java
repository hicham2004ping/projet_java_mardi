package ma.prodenta.config.util;

import java.io.InputStream;
import java.util.Properties;


public class DatabaseConfig {
    private static final Properties props = new Properties();


    static {
        try (InputStream in = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Impossible de charger db.properties", e);
        }
    }


    public static String get(String key) {
        return props.getProperty(key);
    }


    public static String getJdbcUrl() { return get("jdbc.dburl"); }
    public static String getJdbcUser() { return get("jdbc.user"); }
    public static String getJdbcPassword() { return get("jdbc.password"); }
}
