package standAlone;

import javax.swing.*;
import java.awt.*;

class Alert extends JFrame {
    private JLabel message;

    public Alert(String str){
        super.setTitle("알림");
        super.setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        message = new JLabel(str);
        add(message,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}