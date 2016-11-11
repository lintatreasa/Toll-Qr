/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toll;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author bill
 */
public class UserLogin extends JFrame implements WindowListener,ActionListener{
    private Container container=getContentPane();
    private JLabel lblUser=new JLabel("User Name");
    private JLabel lblPass=new JLabel("Password");
    private JTextField txtUser=new JTextField(20);
    private JPasswordField txtPass=new JPasswordField(20);
    private JButton btnLogin=new JButton("Login");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        UserLogin userLogin = new UserLogin();
    }
    public UserLogin(){
        super("User Login");
                setSize(400, 200);
                setLayout(null);  
                final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - this.getWidth()) / 2;
                final int y = (screenSize.height - this.getHeight()) / 2;
                this.setLocation(x, y);
                
                lblUser.setSize(100, 20);
                lblUser.setLocation(140, 40);
                container.add(lblUser);
                
                txtUser.setSize(100, 20);
                txtUser.setLocation(240, 40);
                container.add(txtUser);
                
                lblPass.setSize(100, 20);
                lblPass.setLocation(140, 70);
                container.add(lblPass);
                
                txtPass.setSize(100, 20);
                txtPass.setLocation(240, 70);
                container.add(txtPass);
                
                btnLogin.setSize(100,30);
                btnLogin.setLocation(240, 100);
                container.add(btnLogin);
                
                btnLogin.addActionListener(this);
                this.addWindowListener(this);
                this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
   }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnLogin){
            if(txtUser.getText().isEmpty()||txtPass.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Empty fields");
            }else {
            if(DB.login(txtUser.getText().toString().trim(), txtPass.getText().toString().trim())){
                new User(txtUser.getText().toString().trim(), txtPass.getText().toString().trim());
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Login failed");
            }
            }
        }
    }
    
}
