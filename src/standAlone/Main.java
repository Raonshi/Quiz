package standAlone;

import functions.*;

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

public class Main {

    public static void main(String[] arg) throws Exception {
        MyFrame GUI = new MyFrame();
        DBConnection dbConnection = new DBConnection();
    }
}

class MyFrame extends JFrame{
    private JTabbedPane tap;
    private JPanel loginPanel, quizPanel, userPanel, bottomPanel;
    private JTextField id;
    private JPasswordField pw;
    private JLabel idLabel, pwLabel, logLabel;
    private JButton loginBtn, registerBtn;
    private JButton tableRefreshBtn;
    private JButton joinQuizBtn, manageQuizBtn;
    private JScrollPane scrollPane;
    private JTable userTable;

    int role = 0;
    boolean isLogin = false;
    DefaultTableModel userModel;
    String columns[] = {"No.", "ID", "PW", "Role"};

    public MyFrame(){
        super.setTitle("퀴즈 프로그램");
        super.setSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);

        //로그인 패널 영역
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(1000, 50));
        loginPanel.setLayout(new FlowLayout());

        //id컴포넌트설정
        idLabel = new JLabel("  ID");
        idLabel.setPreferredSize(new Dimension(50, 30));
        id = new JTextField();
        id.setPreferredSize(new Dimension(200, 30));

        //pw컴포넌트설정
        pwLabel = new JLabel("  PW");
        pwLabel.setPreferredSize(new Dimension(50, 30));
        pw = new JPasswordField();
        pw.setPreferredSize(new Dimension(200, 30));

        loginBtn = new JButton("로그인");
        loginBtn.setPreferredSize(new Dimension(200, 30));
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isLogin == false) {
                    Login login = new Login(id.getText(), new String(pw.getPassword()));
                    role = login.LoginAction();
                    switch (role) {
                        case 100:
                        case 110:
                            isLogin = true;
                            loginBtn.setText("로그아웃");
                            registerBtn.setVisible(false);
                            if(role == 100){
                                manageQuizBtn.setVisible(true);
                            }else{
                                manageQuizBtn.setVisible(false);
                            }

                            logLabel.setText(id.getText() + "님, 환영합니다!");
                            break;
                        case 120:
                            isLogin = false;
                            new Alert("가입 대기 중 입니다.");
                            break;
                        default:
                            isLogin = false;
                            new Alert("로그인 정보를 다시 입력해주세요.");
                    }
                }
                else{
                    loginBtn.setText("로그인");
                    registerBtn.setVisible(true);
                    tap.setSelectedIndex(0);
                    isLogin = false;
                }

                tap.setVisible(isLogin);
            }
        });

        registerBtn = new JButton("회원가입");
        registerBtn.setPreferredSize(new Dimension(200, 30));
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
            }
        });

        loginPanel.add(idLabel);
        loginPanel.add(id);
        loginPanel.add(pwLabel);
        loginPanel.add(pw);
        loginPanel.add(loginBtn);
        loginPanel.add(registerBtn);

        add(loginPanel, BorderLayout.NORTH);
        //로그인 패널 영역 끝


        //퀴즈 패널 영역
        quizPanel = new JPanel(new FlowLayout());
        quizPanel.setPreferredSize(new Dimension(1000, 600));

        joinQuizBtn = new JButton("퀴즈 참가");
        joinQuizBtn.setPreferredSize(new Dimension(200, 100));
        joinQuizBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuizJoin();
            }
        });
        quizPanel.add(joinQuizBtn);

        manageQuizBtn = new JButton("퀴즈 관리");
        manageQuizBtn.setPreferredSize(new Dimension(200, 100));
        manageQuizBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuizManage();
            }
        });
        quizPanel.add(manageQuizBtn);

        add(quizPanel, BorderLayout.CENTER);
        //퀴즈 패널 영역 끝


        //회원 패널 영역
        userPanel = new JPanel();
        userPanel.setPreferredSize(new Dimension(1000, 600));
        userPanel.setBackground(Color.PINK);

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(800, 500));

        userTable = new JTable();
        //테이블 모델 생성
        userModel = new DefaultTableModel(columns, 0);
        //테이블 모델과 DB연동
        ModelConnect();
        userTable.setModel(userModel);

        userTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TableModel tm = userTable.getModel();
                int row = userTable.getSelectedRow();

                String id = (String)tm.getValueAt(row, 1);
                String pw = (String)tm.getValueAt(row, 2);
                String role = (String)tm.getValueAt(row, 3);

                new EditUserInfo(row, id, pw, role);
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

        scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        userPanel.add(scrollPane,BorderLayout.NORTH);

        tableRefreshBtn = new JButton("새로고침");
        tableRefreshBtn.setPreferredSize(new Dimension(100, 50));

        tableRefreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userModel = new DefaultTableModel(columns, 0);
                ModelConnect();
                userTable.setModel(userModel);
            }
        });

        userPanel.add(tableRefreshBtn, BorderLayout.SOUTH);

        add(userPanel, BorderLayout.CENTER);
        //회원 패널 영역 끝


        //중앙 탭 영역
        tap = new JTabbedPane();
        tap.add("퀴즈", quizPanel);
        tap.add("회원 관리", userPanel);
        tap.setVisible(isLogin);

        tap.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Component comp = tap.getSelectedComponent();
                if(role == 110 && comp == tap.getComponentAt(1)){
                    userTable.setVisible(false);
                    new Alert("관리자만 접근 할 수 있습니다.");
                }
            }
        });
        add(tap);
        //중앙 탭 영역 끝


        //하단 패널 영역
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(1000, 50));
        bottomPanel.setBackground(Color.GREEN);

        logLabel = new JLabel("This is Log Label");
        logLabel.setPreferredSize(new Dimension(1000, 50));

        bottomPanel.add(logLabel);
        add(bottomPanel, BorderLayout.SOUTH);
        //하단 패널 영역 끝

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void ModelConnect(){

        DBConnection dbConnection = new DBConnection();
        String SQL = "SELECT account_no, account_id, account_password, account_role FROM quiz.account;";
        ResultSet resultSet;
        try{
            resultSet = dbConnection.SearchTable(SQL);
            while(resultSet.next()){
                userModel.addRow(new Object[] {resultSet.getInt("account_no"), resultSet.getString("account_id"),
                        resultSet.getString("account_password"), resultSet.getString("account_role")});
            }
        }
        catch(SQLException sqlException){
            sqlException.getMessage();
        }
    }
}
