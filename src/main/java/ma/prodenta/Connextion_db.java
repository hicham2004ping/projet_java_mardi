package ma.prodenta;
import lombok.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
@Data
public class Connextion_db {
    private final String url;
    private final String username;
    private final String password;
    public Connextion_db() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\Abdo Store\\OneDrive - Ecole Marocaine des Sciences de l'Ingénieur\\Bureau\\projet_java_finale\\src\\main\\resources\\db.properties"));
        url = prop.getProperty("dburl");
        username = prop.getProperty("user");
        password = prop.getProperty("password");
    }
}
