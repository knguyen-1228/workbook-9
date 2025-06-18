package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements Dao<Product>{

    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        String sql = """
                INSERT INTO products (ProductName, CategoryID, UnitPrice)
                VALUES (?,?,?)
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1,product.getName());
            preparedStatement.setInt(2,product.getCategory());
            preparedStatement.setDouble(3,product.getPrice());

            preparedStatement.executeUpdate();

            try(ResultSet keys = preparedStatement.getGeneratedKeys()){
                if(keys.next()){
                    int newId = keys.getInt(1);
                    product.setProductId(newId);
                }
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id,Product product) {
        String sql = """
                DELETE FROM products WHERE ProductID = ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();


        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public void update(int id, Product product) {
        String sql = """
                UPDATE 
                    products
                SET 
                    ProductName = ?,
                    CategoryID = ?,
                    UnitPrice = ?
                WHERE
                    ProductID = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2,product.getCategory());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> searchBy(int id) {

        List<Product> products =new ArrayList<>();

        String sql = """
                SELECT 
                    ProductID,
                    ProductName,
                    CategoryID,
                    UnitPrice
                FROM 
                    products
                WHERE
                    ProductID = ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Product product = new Product();

                    product.setProductId(resultSet.getInt("ProductID"));
                    product.setName(resultSet.getString("ProductName"));
                    product.setCategory(resultSet.getInt("CategoryID"));
                    product.setPrice(resultSet.getDouble("UnitPrice"));

                    products.add(product);
                }
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products =new ArrayList<>();

        String sql = """
                SELECT 
                    ProductID,
                    ProductName,
                    CategoryID,
                    UnitPrice
                FROM
                    products
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                Product product = new Product();

                product.setProductId(resultSet.getInt("ProductID"));
                product.setName(resultSet.getString("ProductName"));
                product.setCategory(resultSet.getInt("CategoryID"));
                product.setPrice(resultSet.getDouble("UnitPrice"));

                products.add(product);
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }
        return products;
    }
}
