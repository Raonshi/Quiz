package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] arg) throws Exception {
        MyThread csThread = new MyThread(101);
        MyThread dbThread = new MyThread(102);

        csThread.start();
        dbThread.start();
    }

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
                    try{
                        dbConnection.SearchTable("quiz.account");
                    }
                    catch (Exception e){
                        System.err.println("쿼리 실행 중 에러가 발생하였습니다 : " + e.getMessage());
                    }
                    break;
            }
        }
    }
}
