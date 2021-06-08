package standAlone;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizCreate extends JFrame {
    private JTabbedPane tap;
    private JComboBox type;

    private JPanel single, multi;
    private JLabel singleTextLabel, singleAnswerLabel;
    private JLabel multiTextLabel, multiAnswerLabel;
    private JTextField singleText, singleAnswer;
    private JTextField multiText, multiAnswer;
    private JLabel num1Label, num2Label, num3Label, num4Label;
    private JTextField num1, num2, num3, num4;
    private JButton apply, cancel;

    public QuizCreate(){
        super.setTitle("퀴즈생성");
        super.setSize(new Dimension(500, 600));
        setLocationRelativeTo(null);

        //주관식
        single = new JPanel();
        single.setPreferredSize(new Dimension(450, 500));
        single.setLayout(new GridLayout(5, 1, 5, 5));

        //퀴즈 지문
        JPanel singleTextPanel = new JPanel();
        singleTextPanel.setLayout(new FlowLayout());
        singleTextLabel = new JLabel("질문");
        singleTextLabel.setPreferredSize(new Dimension(50, 30));
        singleText = new JTextField();
        singleText.setPreferredSize(new Dimension(300, 30));
        singleTextPanel.add(singleTextLabel);
        singleTextPanel.add(singleText);

        //퀴즈 정답
        JPanel singleAnswerPanel = new JPanel();
        singleAnswerPanel.setLayout(new FlowLayout());
        singleAnswerLabel = new JLabel("정답");
        singleAnswerLabel.setPreferredSize(new Dimension(50, 30));
        singleAnswer = new JTextField();
        singleAnswer.setPreferredSize(new Dimension(300, 30));
        singleAnswerPanel.add(singleAnswerLabel);
        singleAnswerPanel.add(singleAnswer);

        single.add(singleTextPanel);
        single.add(singleAnswerPanel);
        add(single);

        //객관식
        multi = new JPanel();
        multi.setPreferredSize(new Dimension(450, 500));
        multi.setLayout(new GridLayout(6, 1, 5, 5));

        //퀴즈 지문
        JPanel multiTextPanel = new JPanel();
        multiTextPanel.setLayout(new FlowLayout());
        multiTextLabel = new JLabel("질문");
        multiTextLabel.setPreferredSize(new Dimension(50, 30));
        multiText = new JTextField();
        multiText.setPreferredSize(new Dimension(300, 30));
        multiTextPanel.add(multiTextLabel);
        multiTextPanel.add(multiText);

        //퀴즈 정답
        JPanel multiAnswerPanel = new JPanel();
        multiAnswerPanel.setLayout(new FlowLayout());
        multiAnswerLabel = new JLabel("정답");
        multiAnswerLabel.setPreferredSize(new Dimension(50, 30));
        multiAnswer = new JTextField();
        multiAnswer.setPreferredSize(new Dimension(300, 30));
        multiAnswerPanel.add(multiAnswerLabel);
        multiAnswerPanel.add(multiAnswer);

        //보기1
        JPanel num1Panel = new JPanel();
        num1Panel.setLayout(new FlowLayout());
        num1Label = new JLabel("보기1");
        num1Label.setPreferredSize(new Dimension(50, 30));
        num1 = new JTextField();
        num1.setPreferredSize(new Dimension(300, 30));
        num1Panel.add(num1Label);
        num1Panel.add(num1);

        //보기2
        JPanel num2Panel = new JPanel();
        num2Panel.setLayout(new FlowLayout());
        num2Label = new JLabel("보기2");
        num2Label.setPreferredSize(new Dimension(50, 30));
        num2 = new JTextField();
        num2.setPreferredSize(new Dimension(300, 30));
        num2Panel.add(num2Label);
        num2Panel.add(num2);

        //보기3
        JPanel num3Panel = new JPanel();
        num3Panel.setLayout(new FlowLayout());
        num3Label = new JLabel("보기3");
        num3Label.setPreferredSize(new Dimension(50, 30));
        num3 = new JTextField();
        num3.setPreferredSize(new Dimension(300, 30));
        num3Panel.add(num3Label);
        num3Panel.add(num3);

        //보기4
        JPanel num4Panel = new JPanel();
        num4Panel.setLayout(new FlowLayout());
        num4Label = new JLabel("보기4");
        num4Label.setPreferredSize(new Dimension(50, 30));
        num4 = new JTextField();
        num4.setPreferredSize(new Dimension(300, 30));
        num4Panel.add(num4Label);
        num4Panel.add(num4);


        multi.add(multiTextPanel);
        multi.add(multiAnswerPanel);
        multi.add(num1Panel);
        multi.add(num2Panel);
        multi.add(num3Panel);
        multi.add(num4Panel);
        add(multi);


        //탭 패널
        tap = new JTabbedPane();
        tap.add("주관식", single);
        tap.add("객관식", multi);
        tap.setVisible(true);
        add(tap, BorderLayout.CENTER);


        //등록 버튼
        JPanel btnPanel = new JPanel(new FlowLayout());

        apply = new JButton("등록");
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //값을 통해 DB에 저장
                //주관식
                if(tap.getSelectedIndex() == 0){
                    new DBConnection().InsertSingleQuizInfo(singleText.getText(), singleAnswer.getText());
                }
                //객관식
                else{
                    int num = Integer.parseInt(multiAnswer.getText());
                    new DBConnection().InsertMultiQuizInfo(multiText.getText(), num1.getText(), num2.getText(), num3.getText(), num4.getText(), num);
                }

                new Alert("등록되었습니다.");
                dispose();
            }
        });

        //취소 버튼
        cancel = new JButton("취소");
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
