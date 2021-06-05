package standAlone;

import javax.swing.*;
import java.awt.*;

public class QuizManage extends JFrame {

    private JButton createBtn, deleteBtn;
    
    public QuizManage(){
        setTitle("퀴즈 관리");
        setSize(new Dimension(800, 450));
        setLocationRelativeTo(null);




        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
