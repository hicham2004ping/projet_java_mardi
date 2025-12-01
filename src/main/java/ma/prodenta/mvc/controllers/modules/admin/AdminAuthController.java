package ma.prodenta.mvc.controllers.modules.admin;

import ma.prodenta.common.exceptions.ValidationException;
import ma.prodenta.mvc.dto.admin.AdminViewDto;
import ma.prodenta.mvc.dto.admin.LoginRequestDto;
import ma.prodenta.service.common.AdminService;

import javax.swing.*;

/**
 relie l'interface Swing et le service d'auth.
 */
public class AdminAuthController {

    private final AdminService adminService;

    public AdminAuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     test connexion
     */
    public AdminViewDto login(String username, String password, JFrame parentFrame) {
        try {
            LoginRequestDto dto = new LoginRequestDto(username, password);
            AdminViewDto admin = adminService.authenticate(dto);
            JOptionPane.showMessageDialog(parentFrame,
                    "Connexion réussie. Bienvenue " + admin.getFullName() + " !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            return admin;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(parentFrame,
                    e.getMessage(),
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE);
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(parentFrame,
                    e.getMessage(),
                    "Erreur d'authentification",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Erreur inattendue : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }
}

