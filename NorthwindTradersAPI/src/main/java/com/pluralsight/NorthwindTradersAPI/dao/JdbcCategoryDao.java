package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.model.Category;
import com.pluralsight.NorthwindTradersAPI.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements Dao<Category> {

    private DataSource dataSource;

    @Autowired
    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Category category) {
        String sql = """
                INSERT INTO categories (CategoryID, CategoryName)
                VALUES (?,?)
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, category.getCategoryId());
            statement.setString(2, category.getCategoryName());

            statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){
                if(keys.next()){
                    int newId = keys.getInt(1);
                    category.setCategoryId(newId);
                }
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }


    }

    @Override
    public void delete(int id, Category category) {
        String sql = """
                DELETE FROM categories WHERE CategoryID = ?
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
    public void update(int id,Category category) {
        String sql = """
                UPDATE 
                    categories
                SET 
                    CategoryID = ?,
                    CategoryName = ?
                WHERE
                    CategoryID = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,category.getCategoryId());
            preparedStatement.setString(2, category.getCategoryName());
            preparedStatement.setInt(3,id);

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public List<Category> searchBy(int id) {
        List<Category> categories =new ArrayList<>();

        String sql = """
                SELECT 
                    CategoryID,
                    CategoryName
                FROM 
                    categories
                WHERE
                    CategoryID = ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Category category = new Category();

                    category.setCategoryId(resultSet.getInt("CategoryID"));
                    category.setCategoryName(resultSet.getString("CategoryName"));

                    categories.add(category);
                }
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories =new ArrayList<>();

        String sql = """
                SELECT 
                    CategoryID,
                    CategoryName
                FROM
                    categories
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                Category category = new Category();

                category.setCategoryId(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));


                categories.add(category);
            }

        }catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }
        return categories;
    }
}
