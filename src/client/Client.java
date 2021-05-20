package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] arg) throws IOException {
        Socket socket = new Socket("localhost", 10110);
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        String str = reader.readLine();
        System.out.println(str);
        System.exit(0);
    }
}
