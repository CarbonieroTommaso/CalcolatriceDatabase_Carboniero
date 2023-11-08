package Database.Connector;

import CalcolatriceRPN.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register {
    public JPanel register;
    private JTextField txtName;
    private JTextField txtCognome;
    private JTextField txtNick;
    private JTextField txtAge;
    private JButton RegisterBtn;
    private JLabel Nome;
    private JLabel Cognome;
    private JPasswordField txtPassword;

    public Register(){

        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB db = new DB();
                JFrame framecalc;
                String nome = txtName.getText();
                String cognome = txtCognome.getText();
                String  nickname = txtNick.getText();
                String password = new String(txtPassword.getPassword());
                int age = Integer.parseInt(txtAge.getText());

                try{
                    if(db.register(nome, cognome, nickname, password, age) == 1){
                        framecalc = new JFrame("Calcolatrice");
                        framecalc.setContentPane(new mainForm().calcolatrice);
                        framecalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        framecalc.setSize(450, 450);
                        framecalc.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Registrazione fallito");
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("RegisterForm");
        frame.setContentPane(new Register().register);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
