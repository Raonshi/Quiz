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

        LoadDriver();
        ConnectDriver();
    }

    //JDBC 드라이버를 로드한다.
    void LoadDriver(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.err.println("JDBC 드라이버 로드 실패! : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //로드된 드라이버를 통해 DB에 연결한다.
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


    //드라이버의 연결을 해제하려는 경우 호출
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

    //회원가입 정보를 DB에 저장
    public void InsertAccountInfo(String id, String pw, String role) throws Exception{
        //현재 가입된 회원 수를 조회한다.
        String searchSQL = "SELECT * FROM quiz.account";
        //쿼리 객체 생성 -> 커서 위치를 이동 시킬 수 있도록 파라미터 값을 넣어준다.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(searchSQL);
        //커서 이동
        resultSet.last();
        int no = resultSet.getRow() + 1;

        //SQL문장을 정의
        String SQL = "INSERT INTO quiz.account(account_no, account_id, account_password, account_role)"
                + "values('"+no+"', '"+id+"', '"+pw+"', '"+role+"');";

        //쿼리 객체 생성
        Statement stmt = connection.createStatement();
        //ResultSet result = null;
        stmt.executeUpdate(SQL);
    }

    //회원 정보를 DB에서 삭제
    public void DeleteAccountInfo(int no) throws Exception{
        //SQL문장을 정의
        String SQL = "DELETE FROM quiz.account WHERE account_no="+no+";";

        //쿼리 객체 생성
        Statement stmt = connection.createStatement();
        //ResultSet result = null;
        stmt.executeUpdate(SQL);
    }
}
