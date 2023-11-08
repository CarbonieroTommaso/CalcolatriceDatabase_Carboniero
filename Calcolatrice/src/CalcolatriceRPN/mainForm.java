package CalcolatriceRPN;

import javax.swing.*;
import java.util.Stack;
import Database.Connector.DB;

public class mainForm {
    private JButton btnCE;
    private JButton btn7;
    private JButton btn4;
    private JButton btn1;
    private JButton btnEsp;
    private JTextField textField1;
    private JButton btn0;
    private JButton btnVirg;
    private JButton btnUguale;
    private JButton btnPiu;
    private JButton btnMeno;
    private JButton btnPer;
    private JButton btnDiv;
    private JButton btnCanc;
    private JButton btnC;
    private JButton btn8;
    private JButton btn9;
    private JButton btn5;
    private JButton btn6;
    private JButton btn2;
    private JButton btn3;
    public JPanel calcolatrice;

    public mainForm() {
        btn0.addActionListener(e -> textField1.setText(textField1.getText()+btn0.getText()));
        btn1.addActionListener(e -> textField1.setText(textField1.getText()+btn1.getText()));
        btn2.addActionListener(e -> textField1.setText(textField1.getText()+btn2.getText()));
        btn3.addActionListener(e -> textField1.setText(textField1.getText()+btn3.getText()));
        btn4.addActionListener(e -> textField1.setText(textField1.getText()+btn4.getText()));
        btn5.addActionListener(e -> textField1.setText(textField1.getText()+btn5.getText()));
        btn6.addActionListener(e -> textField1.setText(textField1.getText()+btn6.getText()));
        btn7.addActionListener(e -> textField1.setText(textField1.getText()+btn7.getText()));
        btn8.addActionListener(e -> textField1.setText(textField1.getText()+btn8.getText()));
        btn9.addActionListener(e -> textField1.setText(textField1.getText()+btn9.getText()));
        btnVirg.addActionListener(e -> textField1.setText(textField1.getText()+btnVirg.getText()));
        btnPer.addActionListener(e -> textField1.setText(textField1.getText()+btnPer.getText()));
        btnPiu.addActionListener(e -> textField1.setText(textField1.getText()+btnPiu.getText()));
        btnMeno.addActionListener(e -> textField1.setText(textField1.getText()+btnMeno.getText()));
        btnDiv.addActionListener(e -> textField1.setText(textField1.getText()+btnDiv.getText()));
        btnC.addActionListener(e -> {
            String bck = null;
            if(textField1.getText().length()>0) {
                StringBuilder str = new StringBuilder(textField1.getText());
                str.deleteCharAt(textField1.getText().length()-1);
                textField1.setText(bck);
            }
        });
        btnCE.addActionListener(e -> {
            String bck = null;
            if(textField1.getText().length()>0) {
                StringBuilder str = new StringBuilder(textField1.getText());
                str.deleteCharAt(textField1.getText().length()-1);
                textField1.setText(bck);
            }
        });
        btnCanc.addActionListener(e -> {
            String bck = null;
            if(textField1.getText().length()>0) {
                StringBuilder str = new StringBuilder(textField1.getText());
                str.deleteCharAt(textField1.getText().length()-1);
                bck = str.toString();
                textField1.setText(bck);
            }
        });
        btnUguale.addActionListener(e -> {
            DB db = new DB();
            String input = textField1.getText();
            String rpnExpression = RPN(input);
            float result = calcoloRPN(rpnExpression);
            textField1.setText(String.valueOf(result));
            db.cronologia(textField1.getText());

            int scelta = JOptionPane.showConfirmDialog(null, "Vuoi vedere la tua cronologia?", "SI", JOptionPane.YES_NO_OPTION);
            if(scelta == JOptionPane.YES_OPTION)
                JOptionPane.showMessageDialog(null, db.stampa());
        });


    }

    public static int Prec(char ch){
        switch (ch){
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    public static String RPN(String exp){
        String result = new String("");

        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < exp.length(); i++){
            char c = exp.charAt(i);

            if(Character.isDigit(c) || c == ' ')
                result += c;
            else if(c == '(')
                stack.push(c);
            else if(c == ')'){
                while(!stack.isEmpty()
                        && stack.peek() != '(') {
                    result += stack.pop();
                }
                stack.pop();
            }
            else{
                result += " ";
                while(!stack.isEmpty()
                        && Prec(c) <= Prec(stack.peek())){
                    result += stack.pop();
                }
                stack.push(c);
            }
        }

        while(!stack.isEmpty()){
            if(stack.peek() == '(')
                return "Invalid Expression";
            result += stack.peek();
            stack.pop();
        }

        return result;
    }

    public static float calcoloRPN(String espressione){
        Stack<Float> stack = new Stack<>();
        String temp = "";
        for(int i = 0; i < espressione.length(); i++){
            switch(espressione.charAt(i)){
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ',':
                    temp += espressione.charAt(i);
                    break;
                case ' ':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    break;
                case '+':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(stack.pop()+stack.pop());
                    break;
                case '-':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(-stack.pop()+stack.pop());
                    break;
                case '/':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(1/stack.pop()*stack.pop());
                    break;
                case '*':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(stack.pop()*stack.pop());
                    break;
            }
        }
        return stack.pop();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("mainForm");
        frame.setContentPane(new mainForm().calcolatrice);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
