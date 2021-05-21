package server;

import java.sql.*;

public class DBConnection {
    Connection connection = null;

    String server = "";
    String database = "";
    String user_name = "";
    String password = "";

    public DBConnection(String server, String database, String user_name, String password){
        this.server = server;
        this.database = database;
        this.user_name = user_name;
        this.password = password;

        //드라이버 로딩
        LoadDriver();

        //드라이버 연결
        ConnectDriver();
    }

    void LoadDriver(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.err.println("JDBC 드라이버 로드 실패! : " + e.getMessage());
            e.printStackTrace();
        }
    }

    void ConnectDriver(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false", user_name, password);
            System.out.println("DB연결에 성공하였습니다.");
        }
        catch (SQLException e){
            System.err.println("DB연결에 실패하였습니다. : " + e.getMessage());
            e.printStackTrace();
        }
    }


    //드라이버 연결 해제시 호출
    public void DisconnectDriver(){
        try{
            if(connection != null){
                connection.close();
                System.out.println("정상적으로 DB연결을 종료하였습니다.");
            }
        }
        catch (SQLException e){
            System.err.println("드라이버 연결 해제 중 오류 발생 : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //테이블 조회
    public void SearchTable(String tableName) throws Exception{
        //SQL문장 정의
        String SQL = "SELECT * FROM " + tableName;

        //쿼리를 보낼 객체와 쿼리 결과를 저장하는 객체 생성
        Statement stmt = connection.createStatement();
        ResultSet result = null;

        //SQL문장 실행
        result = stmt.executeQuery(SQL);

        //출력 부분
        while(result.next()){
            String no = result.getString("account_no");
            String id = result.getString("account_id");
            String pw = result.getString("account_password");
            String role = result.getString("account_role");

            System.out.println(no + "|" + id + "|" + pw + "|" + role);
        }
    }
}
