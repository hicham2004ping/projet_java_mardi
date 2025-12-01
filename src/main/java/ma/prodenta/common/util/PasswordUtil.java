package ma.prodenta.common.util;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
public class PasswordUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String hash(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashed =
                    md.digest(password.getBytes(StandardCharsets.UTF_8));
                        return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateSalt() {
        byte[] s = new byte[16];
        RANDOM.nextBytes(s);
        return Base64.getEncoder().encodeToString(s);
    }
}

