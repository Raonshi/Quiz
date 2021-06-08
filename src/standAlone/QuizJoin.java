package standAlone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class QuizJoin extends JFrame {

    private JPanel qPanel;
    private JLabel text;
    private JLabel num1, num2, num3, num4;
    private JTextField answerText;
    private JButton answerBtn;


    String question_text, question_answer;
    String question_num1, question_num2, question_num3, question_num4;

    public QuizJoin(){
        setTitle("퀴즈 참가");
        setSize(new Dimension(800, 450));
        setLocationRelativeTo(null);

        //출제 패널
        qPanel = new JPanel();

        //출제 지문
        text = new JLabel("");
        text.setPreferredSize(new Dimension(700, 60));
        qPanel.add(text, BorderLayout.NORTH);

        //보기
        JPanel numPanel = new JPanel();
        numPanel.setPreferredSize(new Dimension(700, 200));
        numPanel.setLayout(new GridLayout(2, 2, 5, 5));
        num1 = new JLabel("");
        num2 = new JLabel("");
        num3 = new JLabel("");
        num4 = new JLabel("");
        numPanel.add(num1);
        numPanel.add(num2);
        numPanel.add(num3);
        numPanel.add(num4);
        qPanel.add(numPanel, BorderLayout.CENTER);
        //출제 패널 끝

        //정답 기입
        answerText = new JTextField();
        answerText.setPreferredSize(new Dimension(500, 30));
        qPanel.add(answerText, BorderLayout.SOUTH);



        add(qPanel, BorderLayout.CENTER);
        //정답
        answerBtn = new JButton("채점");
        answerBtn.setPreferredSize(new Dimension(75, 30));
        answerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(answerText.getText().equals(question_answer)){

                    Quest();
                    new Alert("정답입니다.");
                }
                else{
                    new Alert("오답입니다.\n 문제를 다시 풀어보세요.");
                }
            }
        });
        add(answerBtn, BorderLayout.SOUTH);

        Quest();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * 문제 출제 메서드
     */
    void Quest(){
        //문제 초기화
        text.setText("");
        num1.setText("");
        num2.setText("");
        num3.setText("");
        num4.setText("");

        //출제 방법
        //1. 객관식 주관식 랜덤 선택
        Random random = new Random();
        int rand = random.nextInt(2);
        ResultSet resultSet;
        String SQL;
        //객관식 문제 SQL
        if(rand == 0){
            SQL = "SELECT * FROM quiz.question_type2 ORDER BY RAND() LIMIT 1";
        }
        //주관식 문제 SQL
        else{
            SQL = "SELECT * FROM quiz.question_type1 ORDER BY RAND() LIMIT 1";
        }

        //2. 전체 객관식 문제 중 랜덤으로 출제
        try{
            resultSet = new DBConnection().SearchTable(SQL);
            resultSet.next();

            //객관식, 주관식 둘다
            question_text = resultSet.getString("question_text");
            question_answer = resultSet.getString("question_answer");
            //객관식일 경우
            if(rand == 0){
                question_text += "\n(객관식은 정답 번호를 입력해주세요.)";
                question_num1 = "1. " + resultSet.getString("question_num1");
                question_num2 = "2. " + resultSet.getString("question_num2");
                question_num3 = "3. " + resultSet.getString("question_num3");
                question_num4 = "4. " + resultSet.getString("question_num4");
                
            }
            //주관식일 경우 보기란 공백
            else{
                question_num1 = "";
                question_num2 = "";
                question_num3 = "";
                question_num4 = "";
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        text.setText(question_text);
        num1.setText(question_num1);
        num2.setText(question_num2);
        num3.setText(question_num3);
        num4.setText(question_num4);
    }
}
