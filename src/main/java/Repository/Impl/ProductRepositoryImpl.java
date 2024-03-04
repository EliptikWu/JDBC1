package Repository.Impl;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import DB.DataBaseConnection;
import Models.Product;
import Repository.ProductRepository;
import jdk.jfr.Category;


public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void save(Product product) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO product(name, price, dateRegistration, category_id) values(?,?,?,?,?,)")

        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            LocalDateTime dateRegister = product.getDateRegistration();
            preparedStatement.setDate(3, Date.valueOf(dateRegister.toLocalDate()));
            preparedStatement.setLong(4, product.getCategory().getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    
    private Product createProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        java.sql.Date dbSqlDate = resultSet.getDate("dateRegistration");
        if (dbSqlDate != null) {
            LocalDate dateRegister = dbSqlDate.toLocalDate();
            product.setDateRegistration(dateRegister.atStartOfDay());
        } else {
            product.setDateRegistration(null);
        }
        return product;
    }

    public List<Product> list() {
        List<Product> productoList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT p.*, c.name as category_name, c.id as category_id " +
                     "FROM products as p " +
                     "INNER JOIN categories as c ON p.category_id = c.id;")) {
            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                productoList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoList;
    }

    public Product byId(Long id) {
        Product product = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT p.*, c.name as category_name, c .id as category_id " +
                        "FROM products as p " +
                        "INNER JOIN categories AS c On p.category_id = c.id " +
                        "where p.id =p.id=?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

   @Override
    public void update(Product product){
        try(PreparedStatement preparedStatement = getConnection()
       .prepareStatement("UPDATE products SET name = ?, precio = ?, dateRegistration = ?, category_id = ? WHERE id = ?"
       )){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            LocalDateTime dateRegister = product.getDateRegistration();
            preparedStatement.setDate(3, Date.valueOf(dateRegister.toLocalDate()));
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.setLong(5, product.getId());
            preparedStatement.executeUpdate();
       } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   }

   @Override
    public void delete(Long id){
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE FROM Products where id = ?")
        ){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   }

}

