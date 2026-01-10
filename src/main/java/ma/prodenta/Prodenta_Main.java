package ma.prodenta;
import ma.prodenta.mvc.ui.auth.LoginView;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Prodenta_Main {
     static void main(String[] args) throws Exception {
         try {
             UIManager.setLookAndFeel(new FlatLightLaf());
         } catch (Exception ex) {
             ex.printStackTrace();
         }

         javax.swing.SwingUtilities.invokeLater(() -> {
             try {
                 Integer roleId = 2;
                 Dashboard_view frame = new Dashboard_view(roleId);
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
         });

     }
}
