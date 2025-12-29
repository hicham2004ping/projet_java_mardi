package ma.prodenta;
import ma.prodenta.mvc.ui.auth.LoginView;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Prodenta_Main {
     static void main(String[] args) throws Exception {
         try {
             UIManager.setLookAndFeel(new FlatLightLaf()); // or FlatDarkLaf
         } catch (Exception ex) {
             ex.printStackTrace();
         }

         javax.swing.SwingUtilities.invokeLater(() -> {
             try {
                 Dashboard_view frame = new Dashboard_view();
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
         });

     }
}
