package com.pluralsight.NorthwindTrandersSpringBoot.Dao;

import com.pluralsight.NorthwindTrandersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcProductDao implements ProductDao{

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
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1,product.getName());
            preparedStatement.setInt(2,product.getCategory());
            preparedStatement.setDouble(3,product.getPrice());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Product product) {
        String sql = """
                DELETE FROM products WHERE ProductID = ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,product.getProductId());
            preparedStatement.executeUpdate();


        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public void update(Product product) {
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
            preparedStatement.setInt(4,product.getProductId());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public void searchBy(Product product) {

        String sql = """
                SELECT 
                    ProductID,
                    ProductName,
                    CategoryID,
                    UnitPrice
                FROM 
                    products
                WHERE
                    ProductName LIKE ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, product.getName() + "%");

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    System.out.println("ProductID: " + resultSet.getInt("ProductID"));
                    System.out.println("Product Name: " + resultSet.getString("ProductName"));
                    System.out.println("Category ID: " + resultSet.getInt("CategoryID"));
                    System.out.println("Price: " + resultSet.getDouble("UnitPrice"));
                    System.out.println("----------------------------------------------------------------------------------");
                }
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

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
