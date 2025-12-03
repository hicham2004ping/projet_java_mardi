package ma.prodenta.common.validators;

public final class AuthValidator {

    private static final int MIN_LENGTH = 3;

    private AuthValidator() {
    }

    public static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire.");
        }
        if (username.trim().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Le nom d'utilisateur doit contenir au moins " + MIN_LENGTH + " caractères.");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }
        if (password.trim().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins " + MIN_LENGTH + " caractères.");
        }
    }
}
