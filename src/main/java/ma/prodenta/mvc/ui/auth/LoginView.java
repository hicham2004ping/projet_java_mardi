package ma.prodenta.mvc.ui.auth;
import javax.swing.*;
import java.awt.*;
import ma.prodenta.mvc.ui.palette.composants.button.Personaliser_Button;
public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,1000);
        setResizable(false);
        setLocationRelativeTo(null);
        initialise();
    }

    public void initialise(){
        JButton loginButton=new JButton("Se connecter");
        this.setLayout(new GridLayout(3,1));
        JLabel l=new JLabel("Username :");
        JTextField username=new JTextField();
        JLabel l2=new JLabel("Password");
        JPasswordField pass=new JPasswordField();
        add(l);
        add(username);
        add(l2);
        add(pass);
        add(loginButton);
        setVisible(true);
    }
}

