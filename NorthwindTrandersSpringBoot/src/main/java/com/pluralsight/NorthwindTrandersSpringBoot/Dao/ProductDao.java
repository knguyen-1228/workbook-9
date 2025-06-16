package com.pluralsight.NorthwindTrandersSpringBoot.Dao;

import com.pluralsight.NorthwindTrandersSpringBoot.model.Product;

import java.util.List;

public interface ProductDao {

    void add(Product product);

    void delete(Product product);

    void update(Product product);

    void searchBy(Product product);

    List<Product> getAll();
}
