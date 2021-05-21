package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketConnection {

    private int portNumber = 0;

    public SocketConnection(int portNumber) throws Exception{
        this.portNumber = portNumber;
        Connect();
    }

    void Connect() throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        while(true){
            //소켓이 연결될 때까지 무한정 대기한다.
            Socket socket = serverSocket.accept();
            try{
                //소켓이 연결되면 try이하 구문이 실행된다.
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("서버와 연결에 성공하였습니다.");
            }
            catch(Exception e){
                System.out.println("서버와 연결에 실패하였습니다 : " + e.getMessage());
            }
        }
    }
}
