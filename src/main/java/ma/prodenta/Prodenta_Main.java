package ma.prodenta;

import ma.prodenta.mvc.ui.auth.LoginFrame;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Prodenta_Main {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new LoginFrame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
