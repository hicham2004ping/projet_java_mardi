package ma.prodenta.common.validators;

public class PasswordValidator {
    public static boolean isValid(String pwd) {
        if (pwd == null) return false;
        if (pwd.length() < 8) return false;
// règle simple : au moins une majuscule, une minuscule et un chiffre
        boolean hasUpper = pwd.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = pwd.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = pwd.chars().anyMatch(Character::isDigit);
        return hasUpper && hasLower && hasDigit;
    }
}

