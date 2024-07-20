import java.sql.*;
public class Connection1{
    static Connection con;
    public static Connection pconect(){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcare", "root","root1234");
        }catch(Exception e){
            System.out.println("class nhi mili yaar");
        }
        return con;
    }
}