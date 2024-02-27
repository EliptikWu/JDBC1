package Impl;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import Application.DataBaseConnection;
import Application.Product;
import Repository.Repository;


public class RepositoryImpl implements Repository {

    @Override
    public void save(Object o) {

    }

    @Override
    public void delete(Long id) {

    }
    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private Product createProduct(ResultSet resultSet)throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDateRegistration(
                resultSet.getDate("dateRegistration") != null ?
                        resultSet.getDate("date")
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate() :
                        null);
        return product;
    }

    public List<Product> list(){
        List<Product> productoList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT* fromproductos")){
            while(resultSet.next()){
                Product product=createProduct(resultSet);
                productoList.add(product);
            }
        }catch(SQLException e){
            e.printStackTrace(); }
        return productoList;
    }
    public Product byId(Long id){
        Product product = null;
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT*FROMproductosWHEREid =?")){
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                product = createProduct(resultSet);
            }
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace();
        } return product; }
}