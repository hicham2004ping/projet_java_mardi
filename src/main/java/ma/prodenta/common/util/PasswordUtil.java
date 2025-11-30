package ma.prodenta.common.util;

import org.mindrot.jbcrypt.BCrypt; //hachage dl psswd

public class PasswordUtil {

    // rounds = 12 mzyan
    public static String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String plain, String hash) {
        if (plain == null || hash == null) return false;
        return BCrypt.checkpw(plain, hash);
    }
}
