package Application;

import DB.DataBaseConnection;
import Models.Product;
import Repository.Impl.ProductRepositoryImpl;
import Repository.ProductRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main2 {
    public static void main(String[] args){
        try(Connection conn = DataBaseConnection.getInstance()){
            ProductRepository<Product> repository = new ProductRepositoryImpl();
            System.out.println("***List products from database");
            repository.list().stream().forEach(System.out::println);
            System.out.println("**** Get by id: 1");
            System.out.println(repository.byId(1L).toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
