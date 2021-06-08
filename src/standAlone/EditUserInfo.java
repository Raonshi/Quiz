package standAlone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserInfo extends JFrame {
    private JPanel infoPanel, idPanel, pwPanel, rolePanel, btnPanel;
    private JLabel idLabel, pwLabel, roleLabel;
    private JTextField id, role;
    private JPasswordField pw;
    private JButton apply, cancel;

    public EditUserInfo(int rowNum, String _id, String _pw, String _role){
        setTitle("알림");
        setSize(new Dimension(400, 300));

        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(300, 220));

        idPanel = new JPanel(new FlowLayout());
        idPanel.setPreferredSize(new Dimension(250, 70));
        idLabel = new JLabel("ID");
        idLabel.setPreferredSize(new Dimension(50, 30));
        idPanel.add(idLabel);
        id = new JTextField();
        id.setText(_id);
        id.setPreferredSize(new Dimension(150, 30));
        idPanel.add(id);
        infoPanel.add(idPanel, BorderLayout.NORTH);

        pwPanel = new JPanel(new FlowLayout());
        pwPanel.setPreferredSize(new Dimension(250, 70));
        pwLabel = new JLabel("PW");
        pwLabel.setPreferredSize(new Dimension(50, 30));
        pwPanel.add(pwLabel);
        pw = new JPasswordField();
        pw.setText(_pw);
        pw.setPreferredSize(new Dimension(150, 30));
        pwPanel.add(pw);
        infoPanel.add(pwPanel, BorderLayout.CENTER);

        rolePanel = new JPanel(new FlowLayout());
        rolePanel.setPreferredSize(new Dimension(250, 70));
        roleLabel = new JLabel("등급");
        roleLabel.setPreferredSize(new Dimension(50, 30));
        rolePanel.add(roleLabel);
        role = new JTextField();
        role.setText(_role);
        role.setPreferredSize(new Dimension(150, 30));
        rolePanel.add(role);
        infoPanel.add(rolePanel, BorderLayout.SOUTH);

        add(infoPanel, BorderLayout.NORTH);

        btnPanel = new JPanel(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(300, 70));

        apply = new JButton("저장");
        apply.setPreferredSize(new Dimension(75, 30));
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DBConnection().
                        UpdateAccountInfo(rowNum, id.getText(), new String(pw.getPassword()), role.getText());
                dispose();
            }
        });
        btnPanel.add(apply);

        cancel = new JButton("취소");
        cancel.setPreferredSize(new Dimension(75, 30));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnPanel.add(cancel);

        add(btnPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
