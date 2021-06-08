package standAlone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMultiQuizInfo  extends JFrame {
    private JPanel panel;
    private JLabel qLabel, aLabel, n1Label, n2Label, n3Label, n4Label;
    private JTextField q, n1, n2, n3, n4, a;
    private JButton apply, cancel;

    public EditMultiQuizInfo(int row, String _q, String _n1, String _n2, String _n3, String _n4, int _a) {
        setTitle("객관식 퀴즈 수정");
        setSize(new Dimension(400, 600));
        setLocationRelativeTo(null);

        //퀴즈 정보 패널
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 5, 5));
        panel.setPreferredSize(new Dimension(300, 500));

        //퀴즈 지문
        JPanel titlePanel = new JPanel(new FlowLayout());
        qLabel = new JLabel("Question");
        qLabel.setPreferredSize(new Dimension(50, 30));

        q = new JTextField();
        q.setText(_q);
        q.setPreferredSize(new Dimension(250, 30));

        titlePanel.add(qLabel);
        titlePanel.add(q);
        panel.add(titlePanel);

        //보기1
        JPanel n1Panel = new JPanel(new FlowLayout());

        n1Label = new JLabel("보기 1번");
        n1Label.setPreferredSize(new Dimension(50, 30));

        n1 = new JTextField();
        n1.setText(_n1);
        n1.setPreferredSize(new Dimension(250, 30));

        n1Panel.add(n1Label);
        n1Panel.add(n1);
        panel.add(n1Panel);

        //보기2
        JPanel n2Panel = new JPanel(new FlowLayout());

        n2Label = new JLabel("보기 2번");
        n2Label.setPreferredSize(new Dimension(50, 30));

        n2 = new JTextField();
        n2.setText(_n2);
        n2.setPreferredSize(new Dimension(250, 30));

        n2Panel.add(n2Label);
        n2Panel.add(n2);
        panel.add(n2Panel);

        //보기3
        JPanel n3Panel = new JPanel(new FlowLayout());

        n3Label = new JLabel("보기 3번");
        n3Label.setPreferredSize(new Dimension(50, 30));

        n3 = new JTextField();
        n3.setText(_n3);
        n3.setPreferredSize(new Dimension(250, 30));

        n3Panel.add(n3Label);
        n3Panel.add(n3);
        panel.add(n3Panel);

        //보기4
        JPanel n4Panel = new JPanel(new FlowLayout());

        n4Label = new JLabel("보기 4번");
        n4Label.setPreferredSize(new Dimension(50, 30));

        n4 = new JTextField();
        n4.setText(_n4);
        n4.setPreferredSize(new Dimension(250, 30));

        n4Panel.add(n4Label);
        n4Panel.add(n4);
        panel.add(n4Panel);

        //정답
        JPanel aPanel = new JPanel(new FlowLayout());
        aPanel.setPreferredSize(new Dimension(300, 70));

        aLabel = new JLabel("Answer");
        aLabel.setPreferredSize(new Dimension(50, 30));

        a = new JTextField();
        String tmp = Integer.toString(_a);
        a.setText(tmp);
        a.setPreferredSize(new Dimension(250, 30));

        aPanel.add(aLabel);
        aPanel.add(a);
        panel.add(aPanel);

        //버튼
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(300, 70));

        apply = new JButton("저장");
        apply.setPreferredSize(new Dimension(75, 30));
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tmp = Integer.parseInt(a.getText());
                new DBConnection().
                        UpdateMultiQuizInfo(row + 1, q.getText(), n1.getText(), n2.getText(), n3.getText(), n4.getText(), tmp);
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

        panel.add(btnPanel);

        add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}