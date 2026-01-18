package ma.prodenta.common.util;

import ma.prodenta.entities.En.Utilisateur;

public class UserSession {

    private static UserSession instance;
    private Utilisateur currentUser;

    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(Utilisateur user) {
        this.currentUser = user;
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public Integer getCurrentRoleId() {
        return currentUser != null ? currentUser.getIdRole() : null;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public void clear() {
        currentUser = null;
    }
}
