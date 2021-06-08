package functions;

import standAlone.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private String id;
    private String pw;

    //로그인 요청 객체 생성
    public Login(String id, String pw){
        this.id = id;
        this.pw = pw;
    }

    //DB 테이블의 계정값과 비교
    public int LoginAction(){
        //DB연결
        DBConnection dbConnection = new DBConnection();
        String SQL = "SELECT quiz.account.account_id, quiz.account.account_password, quiz.account.account_role " +
                "FROM quiz.account " +
                "WHERE account_id = '" + id + "' && account_password = '" + pw + "';";

        //쿼리 결과
        try {
            ResultSet result = dbConnection.SearchTable(SQL);
            result.next();
            return RoleCheck(result.getString("account_role"));
        }
        catch (SQLException e){
            System.out.println("로그인 시도 중 DB에러가 발생하였습니다 : " + e.getMessage());
            return 999;
        }
    }

    //로그인 계정의 관리 등급 체크
    public int RoleCheck(String role){
        if(role.equals("admin")){
            return 100;
        }
        else if(role.equals("user")){
            return 110;
        }
        else if(role.equals("waiting")){
            return 120;
        }
        else{
            return 999;
        }
    }
}
