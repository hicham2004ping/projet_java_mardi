package ma.prodenta.mvc.ui.palette.composants.button;
import javax.swing.*;
import java.awt.*;

public class Personaliser_Button extends JButton {
    public Personaliser_Button(String text) {
        super(text);
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBackground(new Color(52, 152, 219));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
