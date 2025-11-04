package ma.prodenta.mvc.ui.demarage;
import javax.swing.*;
import java.awt.*;
public class LoginView extends JFrame {
    public LoginView() {
        initialise();
    }
    public void initialise(){
        //configuration du frame
        setTitle("Login");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setResizable(false);
        //declaration
        JPanel left=new JPanel();
        JPanel right=new JPanel();
        JPanel main_panel=new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        GridBagLayout gridbag=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        JLabel label1=new JLabel("Nom : ");
        JTextField nom=new JTextField(20);
        right.setLayout(gridbag);
        left.setPreferredSize(new Dimension(300,400));
        left.setBackground(Color.BLACK);
        //ajouter les composant au right panel
        gbc.gridx=0;
        gbc.gridy=0;
        right.add(label1,gbc);
        gbc.gridx=2;
        gbc.gridy=0;
        right.add(nom,gbc);
        main_panel.add(right);
        add(left,BorderLayout.WEST);
        add(main_panel,BorderLayout.CENTER);
        setVisible(true);
    }
}
