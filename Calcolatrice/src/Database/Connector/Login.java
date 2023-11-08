package Database.Connector;

import CalcolatriceRPN.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField txtNick;
    private JTextField txtPassword;
    private JButton LoginBtn;
    private JButton RegisterBtn;
    public JPanel login;

    private DB db = new DB();

    public Login(){

        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = txtNick.getText();
                String password = txtPassword.getText();
                JFrame framecalc;

                if(db.login(nickname, password) == 1){
                    framecalc = new JFrame("Calcolatrice");
                    framecalc.setContentPane(new mainForm().calcolatrice);
                    framecalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    framecalc.setSize(450, 450);
                    framecalc.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Login Fallito");
                    txtNick.setText("");
                    txtPassword.setText("");
                }
            }
        });
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame registerForm = new JFrame("Registrazione");
                registerForm.setContentPane(new Register().register);
                registerForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                registerForm.pack();
                registerForm.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new Login().login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
