package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static void main(String[] arg) throws Exception {
        /*
        MyThread csThread = new MyThread(101);
        MyThread dbThread = new MyThread(102);

        csThread.start();
        dbThread.start();
*/
        try{
            SocketConnection socketConnection = new SocketConnection(10110);
        }
        catch (Exception e){
            System.err.println("소켓 연결 에러 발생");
        }

        DBConnection dbConnection = new DBConnection("localhost", "quiz", "root", "tnsdnjs2@");


        MyFrame GUI = new MyFrame();
    }



    //멀티 스레드 구현
    static class MyThread extends Thread{
        /*
        job 숫자별 작업 분류
        101 : 클라이언트와 소켓 통신
        102 : DB서버와 통신
        999 : 연결된 작업이 없음.
         */
        int job = 999;

        public MyThread(int job){
            this.job = job;
        }

        @Override
        public void run(){
            switch(job){
                case 101:
                    try{
                        SocketConnection socketConnection = new SocketConnection(10110);
                    }
                    catch (Exception e){
                        System.err.println("소켓 연결 에러 발생");
                    }
                    break;
                case 102:
                    DBConnection dbConnection = new DBConnection("localhost", "quiz", "root", "tnsdnjs2@");
                    break;
            }
        }
    }
}

class MyFrame extends JFrame{
    private JPanel loginPanel, centerPanel, bottomPanel;
    private JTextField id, pw;
    private JLabel idLabel, pwLabel, logLabel;
    private JButton loginBtn;

    public MyFrame(){
        super.setTitle("퀴즈 프로그램(관리자용)");
        super.setSize(new Dimension(1100, 700));

        //로그인 패널 영역
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(1000, 50));
        loginPanel.setLayout(new FlowLayout());
        //테스트용 배경
        loginPanel.setBackground(Color.BLUE);

        //id컴포넌트설정
        idLabel = new JLabel("  ID");
        idLabel.setPreferredSize(new Dimension(100, 30));
        id = new JTextField();
        id.setPreferredSize(new Dimension(300, 30));

        //pw컴포넌트설정
        pwLabel = new JLabel("  PW");
        pwLabel.setPreferredSize(new Dimension(100, 30));
        pw = new JTextField();
        pw.setPreferredSize(new Dimension(300, 30));

        loginBtn = new JButton("로그인");
        loginBtn.setPreferredSize(new Dimension(200, 30));
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        loginPanel.add(idLabel);
        loginPanel.add(id);
        loginPanel.add(pwLabel);
        loginPanel.add(pw);
        loginPanel.add(loginBtn);

        add(loginPanel, BorderLayout.NORTH);
        //로그인 패널 영역 끝


        //중앙 패널 영역
        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(1000, 600));
        centerPanel.setBackground(Color.RED);


        add(centerPanel, BorderLayout.CENTER);
        //중앙 패널 영역 끝


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
}
