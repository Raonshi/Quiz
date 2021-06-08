package standAlone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditSingleQuizInfo  extends JFrame {
    private JPanel qPanel, aPanel, btnPanel;
    private JLabel qLabel, aLabel;
    private JTextField q, a;
    private JButton apply, cancel;

    public EditSingleQuizInfo(int rowNum, String _q, String _a) {
        setTitle("알림");
        setSize(new Dimension(400, 300));
        setLocationRelativeTo(null);

        //퀴즈 지문
        qPanel = new JPanel(new FlowLayout());
        qPanel.setPreferredSize(new Dimension(300, 70));

        qLabel = new JLabel("Question");
        qLabel.setPreferredSize(new Dimension(50, 30));

        q = new JTextField();
        q.setText(_q);
        q.setPreferredSize(new Dimension(200, 30));

        qPanel.add(qLabel);
        qPanel.add(q);
        add(qPanel, BorderLayout.NORTH);

        //퀴즈 정답
        aPanel = new JPanel(new FlowLayout());
        aPanel.setPreferredSize(new Dimension(300, 70));

        aLabel = new JLabel("Answer");
        aLabel.setPreferredSize(new Dimension(50, 30));

        a = new JTextField();
        a.setText(_a);
        a.setPreferredSize(new Dimension(200, 30));

        aPanel.add(aLabel);
        aPanel.add(a);
        add(aPanel, BorderLayout.CENTER);

        //버튼
        btnPanel = new JPanel(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(300, 70));

        apply = new JButton("저장");
        apply.setPreferredSize(new Dimension(75, 30));
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DBConnection().
                        UpdateSingleQuizInfo(rowNum, q.getText(), a.getText());
                dispose();
            }
        });

        cancel = new JButton("취소");
        cancel.setPreferredSize(new Dimension(75, 30));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnPanel.add(apply);
        btnPanel.add(cancel);

        add(btnPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}