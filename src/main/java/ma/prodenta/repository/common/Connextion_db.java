package ma.prodenta.repository.common;
import lombok.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Data
public class Connextion_db {
    private final String url;
    private  final String username;
    private  final String password;
    public Connextion_db() throws IOException {
        Properties prop = new Properties();
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IOException("Le fichier db.properties est introuvable dans le classpath");
            }
            prop.load(in);
        }
        url = prop.getProperty("dburl");
        username = prop.getProperty("user");
        password = prop.getProperty("password");
    }
}