package ma.prodenta.mvc.controllers.modules.admin;
import ma.prodenta.mvc.dto.UserDTO;
public class AdminController {
    //gérer utilisateurs
    public void openAdminDashboard(UserDTO admin) {
        ma.prodenta.mvc.ui.admin.AdminDashboardFrame frame = new
                ma.prodenta.mvc.ui.admin.AdminDashboardFrame(admin);
        frame.setVisible(true);
    }
}
