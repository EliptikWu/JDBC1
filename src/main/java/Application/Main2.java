package Application;

import Impl.RepositoryImpl;
import Repository.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main2 {
    public static void main(String[] args){
        try(Connection conn = DataBaseConnection.getInstance()){
            Repository<Product> repository = new RepositoryImpl();
            System.out.println("***List products from database");
            repository.list().stream().forEach(System.out::println);
            System.out.println("**** Get by id: 1");
            System.out.println(repository.byId(1L).toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
