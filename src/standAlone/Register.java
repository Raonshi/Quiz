package standAlone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame{
    private JPanel idPanel, pwPanel;
    private JLabel idLabel, pwLabel;
    private JTextField id;
    private JPasswordField pw;
    private JButton btn;

    Register(){
        super.setTitle("회원가입");
        super.setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);

        idPanel = new JPanel(new FlowLayout());
        idPanel.setPreferredSize(new Dimension(200, 70));

        idLabel = new JLabel("ID");
        idLabel.setPreferredSize(new Dimension(50, 30));
        idPanel.add(idLabel);

        id = new JTextField();
        id.setPreferredSize(new Dimension(100, 30));
        idPanel.add(id);

        add(idPanel, BorderLayout.NORTH);

        pwPanel = new JPanel(new FlowLayout());
        pwPanel.setPreferredSize(new Dimension(200, 70));

        pwLabel = new JLabel("PW");
        pwLabel.setPreferredSize(new Dimension(50, 30));
        pwPanel.add(pwLabel);

        pw = new JPasswordField();
        pw.setPreferredSize(new Dimension(100, 30));
        pwPanel.add(pw);

        add(pwPanel, BorderLayout.CENTER);

        btn = new JButton("등록 신청");
        btn.setPreferredSize(new Dimension(100, 30));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String identify = id.getText();
                String password = new String(pw.getPassword());

                if(identify.replaceAll(" ","").equals("")){
                    Alert alert = new Alert("입렵 값 오류!");
                }
                else{
                    DBConnection dbConnection = new DBConnection();
                    dbConnection.InsertAccountInfo(identify, password, "waiting");
                }
                dispose();
            }
        });
        add(btn, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
