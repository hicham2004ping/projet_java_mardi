package ma.prodenta.tools;
import ma.prodenta.common.util.PasswordUtil;

public class GenerateHash {
    public static void main(String[] args) {
        String pwd = "admin";//test
        String h = PasswordUtil.hashPassword(pwd);
        System.out.println("HASH = " + h);
    }
}
