package standAlone;

import java.sql.*;

public class DBConnection {
    Connection connection = null;
    //테스트용 서버
    //String server = "localhost";
    //String database = "quiz";
    //String user_name = "root";
    //String password = "tnsdnjs2@";


    //실제 접속 서버
    String server = "52.231.66.86:3306";
    String database = "quiz";
    String user_name = "sunwonsw95";
    String password = "tnsdnjs2@";

    public DBConnection(){
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
    public ResultSet SearchTable(String SQL) throws SQLException{
        //테스트용 SQL문장 정의
        //String testSQL = "SELECT * FROM " + tableName;

        //쿼리를 보낼 객체와 쿼리 결과를 저장하는 객체 생성
        Statement stmt = connection.createStatement();
        ResultSet result = null;

        //SQL문장 실행
        result = stmt.executeQuery(SQL);
        return result;
    }

    //회원가입 정보를 DB에 저장
    public void InsertAccountInfo(String id, String pw, String role){
        try{
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
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void UpdateAccountInfo(int rowNum, String id, String pw, String role){
        try{
            //SQL문장을 정의
            String SQL = "UPDATE quiz.account " +
                    "SET account_id=?, account_password=?, account_role=? " +
                    "WHERE account_no=?";

            //쿼리 객체 생성
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            pstmt.setString(3, role);
            pstmt.setInt(4, rowNum+1);
            pstmt.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
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

    //회원가입 정보를 DB에 저장
    public void InsertSingleQuizInfo(String question, String answer){
        try{
            //SQL문장을 정의
            String SQL = "INSERT INTO quiz.question_type1(question_text, question_answer)"
                    + "values(?, ?);";

            //쿼리 객체 생성
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, question);
            pstmt.setString(2, answer);
            pstmt.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    //회원가입 정보를 DB에 저장
    public void InsertMultiQuizInfo(String question, String num1, String num2, String num3, String num4, int answer){
        try{
            //SQL문장을 정의
            String SQL = "INSERT INTO quiz.question_type2(question_text, question_num1, question_num2, question_num3, question_num4, question_answer)"
                    + "values(?, ?, ?, ?, ?, ?);";

            //쿼리 객체 생성
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, question);
            pstmt.setString(2, num1);
            pstmt.setString(3, num2);
            pstmt.setString(4, num3);
            pstmt.setString(5, num4);
            pstmt.setInt(6, answer);
            pstmt.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void UpdateSingleQuizInfo(int rowNum, String question, String answer){
        try{
            //SQL문장을 정의
            String SQL = "UPDATE quiz.question_type1 " +
                    "SET question_text=?, question_answer=?" +
                    "WHERE question_no=?";
            //쿼리 객체 생성
            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, question);
            pstmt.setString(2, answer);
            pstmt.setInt(3, rowNum+1);

            pstmt.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void UpdateMultiQuizInfo(int rowNum, String question, String n1, String n2, String n3, String n4, int answer){
        try{
            //SQL문장을 정의
            String SQL = "UPDATE quiz.question_type2 " +
                    "SET question_text=?, question_num1=?, question_num2=?, question_num3=?, question_num4=?, question_answer=? " +
                    "WHERE question_no=?";
            //쿼리 객체 생성
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, question);
            pstmt.setString(2, n1);
            pstmt.setString(3, n2);
            pstmt.setString(4, n3);
            pstmt.setString(5, n4);
            pstmt.setInt(6, answer);
            pstmt.setInt(7, rowNum);
            pstmt.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void DeleteQuizInfo(int rowNum, boolean isMulti){
        //SQL문장을 정의
        String SQL;
        if(isMulti){
            SQL = "DELETE FROM quiz.question_type2 WHERE question_no=?;";
        }else{
            SQL = "DELETE FROM quiz.question_type1 WHERE question_no=?;";
        }

        //쿼리 객체 생성
        try{
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setInt(1, rowNum);
            pstmt.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }



}
