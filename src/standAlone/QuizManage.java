package standAlone;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizManage extends JFrame {
    private JTabbedPane tap;
    private JPanel quizPanel, singlePanel, multiPanel, btnPanel;
    private JButton createBtn, deleteBtn, refreshBtn;
    private JTable singleTable, multiTable;
    private JScrollPane singleScroll, multiScroll;


    DefaultTableModel singleModel, multiModel;
    int tapNum;
    
    public QuizManage(){
        //화면 사이즈 설정
        setTitle("퀴즈 관리");
        setSize(new Dimension(950, 700));
        setLocationRelativeTo(null);

        //퀴즈 패널
        quizPanel = new JPanel();

        //퀴즈패널 - 객관식
        singlePanel = new JPanel();
        singlePanel.setPreferredSize(new Dimension(900, 550));

        //테이블 생성
        singleTable = new JTable();
        //테이블 모델 생성
        String singleCol[] = {"No.", "Question", "Answer"};
        singleModel = new DefaultTableModel(singleCol, 0);
        //테이블 모델과 DB연동
        ModelConnect(false);
        singleTable.setModel(singleModel);

        //테이블 클릭 액션
        singleTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TableModel tm = singleTable.getModel();
                int row = singleTable.getSelectedRow();

                String q = (String)tm.getValueAt(row, 1);
                String a = (String)tm.getValueAt(row, 2);

                new EditSingleQuizInfo(row, q, a);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //패널에 추가
        singleScroll = new JScrollPane(singleTable);
        singleScroll.setPreferredSize(new Dimension(800, 500));
        singlePanel.add(singleScroll);
        //퀴즈패널 - 객관식 끝



        //퀴즈패널 - 주관식
        multiPanel = new JPanel();
        multiPanel.setPreferredSize(new Dimension(900, 550));

        //테이블 생성
        multiTable = new JTable();
        //테이블 모델 생성
        String multiCol[] = {"No.", "Question", "NUM 1", "NUM 2", "NUM 3", "NUM 4", "Answer"};
        multiModel = new DefaultTableModel(multiCol, 0);
        //테이블 모델과 DB연동
        ModelConnect(true);
        multiTable.setModel(multiModel);

        //테이블 클릭 액션
        multiTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TableModel tm = multiTable.getModel();
                int row = multiTable.getSelectedRow();

                String q = (String)tm.getValueAt(row, 1);
                String n1 = (String)tm.getValueAt(row, 2);
                String n2 = (String)tm.getValueAt(row, 3);
                String n3 = (String)tm.getValueAt(row, 4);
                String n4 = (String)tm.getValueAt(row, 5);
                int a = (Integer) tm.getValueAt(row, 6);

                new EditMultiQuizInfo(row, q, n1, n2, n3, n4, a);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //패널에 추가
        multiScroll = new JScrollPane(multiTable);
        multiScroll.setPreferredSize(new Dimension(800, 500));
        multiPanel.add(multiScroll);
        //퀴즈패널 - 주관식 끝

        //탭 패널
        tap = new JTabbedPane();
        tap.add("주관식", singlePanel);
        tap.add("객관식", multiPanel);
        tap.setVisible(true);

        tap.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tapNum = tap.getSelectedIndex();
            }
        });
        quizPanel.add(tap);
        //탭 패널 끝

        add(quizPanel, BorderLayout.NORTH);


        //버튼 패널
        btnPanel = new JPanel(new FlowLayout());

        createBtn = new JButton("퀴즈생성");
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuizCreate();
            }
        });

        deleteBtn = new JButton("퀴즈 삭제");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row;
                if(tapNum == 0){
                    row = singleTable.getSelectedRow();
                    new DBConnection().DeleteQuizInfo(row, false);
                }else{
                    row = multiTable.getSelectedRow();
                    new DBConnection().DeleteQuizInfo(row, true);
                }


                new Alert("삭제되었습니다.");
            }
        });

        refreshBtn = new JButton("새로고침");
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                singleModel = new DefaultTableModel(singleCol, 0);
                ModelConnect(false);
                singleTable.setModel(singleModel);

                multiModel = new DefaultTableModel(multiCol, 0);
                ModelConnect(true);
                multiTable.setModel(multiModel);

            }
        });


        btnPanel.add(createBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);

        add(btnPanel, BorderLayout.SOUTH);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void ModelConnect(boolean isMulti){
        DBConnection dbConnection = new DBConnection();
        ResultSet resultSet;
        if(isMulti){
            String SQL = "SELECT question_no, question_text, question_num1, question_num2, question_num3, question_num4, question_answer FROM quiz.question_type2;";
            try{
                resultSet = dbConnection.SearchTable(SQL);
                while(resultSet.next()){
                    multiModel.addRow(new Object[] {
                            resultSet.getInt("question_no"),
                            resultSet.getString("question_text"),
                            resultSet.getString("question_num1"),
                            resultSet.getString("question_num2"),
                            resultSet.getString("question_num3"),
                            resultSet.getString("question_num4"),
                            resultSet.getInt("question_answer"),
                    });
                }
            }
            catch(SQLException sqlException){
                sqlException.getMessage();
            }
        }
        else{
            String SQL = "SELECT question_no, question_text, question_answer FROM quiz.question_type1;";
            try{
                resultSet = dbConnection.SearchTable(SQL);
                while(resultSet.next()){
                    singleModel.addRow(new Object[] {
                            resultSet.getInt("question_no"),
                            resultSet.getString("question_text"),
                            resultSet.getString("question_answer"),
                    });
                }
            }
            catch(SQLException sqlException){
                sqlException.getMessage();
            }
        }
    }
}