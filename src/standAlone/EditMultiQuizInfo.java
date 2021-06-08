package standAlone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMultiQuizInfo  extends JFrame {
    private JPanel qPanel, aPanel, btnPanel;
    private JLabel qLabel, aLabel, n1Label, n2Label, n3Label, n4Label;
    private JTextField q, n1, n2, n3, n4, a;
    private JButton apply, cancel;

    public EditMultiQuizInfo(int row, String _q, String _n1, String _n2, String _n3, String _n4, int _a) {
        setTitle("객관식 퀴즈 수정");
        setSize(new Dimension(400, 600));
        setLocationRelativeTo(null);

        //퀴즈 정보 패널
        qPanel = new JPanel();
        qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.Y_AXIS));
        qPanel.setPreferredSize(new Dimension(300, 220));
        qPanel = new JPanel(new FlowLayout());
        qPanel.setPreferredSize(new Dimension(250, 70));

        //퀴즈 지문
        qLabel = new JLabel("Question");
        qLabel.setPreferredSize(new Dimension(50, 30));
        qPanel.add(qLabel);
        q = new JTextField();
        q.setText(_q);
        q.setPreferredSize(new Dimension(250, 30));
        qPanel.add(q);

        //보기
        n1Label = new JLabel("보기 1번");
        n1Label.setPreferredSize(new Dimension(50, 30));
        n1 = new JTextField();
        n1.setText(_n1);
        n1.setPreferredSize(new Dimension(250, 30));
        qPanel.add(n1Label);
        qPanel.add(n1);

        n2Label = new JLabel("보기 2번");
        n2Label.setPreferredSize(new Dimension(50, 30));
        n2 = new JTextField();
        n2.setText(_n2);
        n2.setPreferredSize(new Dimension(250, 30));
        qPanel.add(n2Label);
        qPanel.add(n2);

        n3Label = new JLabel("보기 3번");
        n3Label.setPreferredSize(new Dimension(50, 30));
        n3 = new JTextField();
        n3.setText(_n3);
        n3.setPreferredSize(new Dimension(250, 30));
        qPanel.add(n3Label);
        qPanel.add(n3);

        n4Label = new JLabel("보기 4번");
        n4Label.setPreferredSize(new Dimension(50, 30));
        n4 = new JTextField();
        n4.setText(_n4);
        n4.setPreferredSize(new Dimension(250, 30));
        qPanel.add(n4Label);
        qPanel.add(n4);

        aPanel = new JPanel(new FlowLayout());
        aPanel.setPreferredSize(new Dimension(300, 70));
        aLabel = new JLabel("Answer");
        aLabel.setPreferredSize(new Dimension(50, 30));
        a = new JTextField();
        String tmp = Integer.toString(_a);
        a.setText(tmp);
        a.setPreferredSize(new Dimension(250, 30));
        qPanel.add(aLabel);
        qPanel.add(a);

        add(qPanel, BorderLayout.NORTH);

        btnPanel = new JPanel(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(300, 70));
        apply = new JButton("저장");
        apply.setPreferredSize(new Dimension(75, 30));
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tmp = Integer.parseInt(a.getText());
                new DBConnection().
                        UpdateMultiQuizInfo(row, q.getText(), n1.getText(), n2.getText(), n3.getText(), n4.getText(), tmp);
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