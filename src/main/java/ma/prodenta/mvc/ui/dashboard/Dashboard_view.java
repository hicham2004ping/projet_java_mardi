package ma.prodenta.mvc.ui.dashboard;
import ma.prodenta.entities.En.Utilisateur;
import javax.swing.*;
import java.awt.*;
import ma.prodenta.mvc.ui.palette.dashboard.Sidebar;
import ma.prodenta.mvc.ui.palette.dashboard.Header_bar;

public class Dashboard_view extends JFrame {
    public Dashboard_view() {
        initialise();
    }
    public  void initialise(){
        setTitle("Prodenta Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,900);
        setResizable(false);
        setLocationRelativeTo(null);
        Sidebar side_bar=new Sidebar();
        Header_bar header_bar=new Header_bar();
        add(side_bar,BorderLayout.WEST);
        add(header_bar,BorderLayout.NORTH);
        setVisible(true);
    }
}
