package ma.prodenta.mvc.ui.palette.dashboard;
import javax.swing.*;
import java.awt.*;

public class Header_bar extends JPanel {
    public Header_bar() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 70));
        setLayout(new BorderLayout());
        JLabel lb = new JLabel("tout ira mieux demain ");
        lb.setHorizontalAlignment(SwingConstants.LEFT);
        lb.setVerticalAlignment(SwingConstants.CENTER);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lb, BorderLayout.CENTER);
    }
}
