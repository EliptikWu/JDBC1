package Application;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/test";
        String user="root";
        String password="admin";
        try(
                java.sql.Connection conn = DriverManager.getConnection(url,user,
                        password);
                Statement statement = conn.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT*FROM " +
                        "products");
        ){
            while(resultSet.next()){
                System.out.print(resultSet.getInt("id"));
                System.out.print("|");
                System.out.print(resultSet.getString("name"));
                System.out.print("|");
                System.out.print(resultSet.getDouble("price"));
                System.out.print("|");
                System.out.print(resultSet.getDate("dateRegistration")); }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}