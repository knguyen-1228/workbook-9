package com.pluralsight.NorthwindTradersAPI.dao;


import com.pluralsight.NorthwindTradersAPI.model.Product;

import java.util.List;

public interface Dao<T> {

    void add(T t);

    void delete(int id,T t);

    void update(int id,T t);

    List<T> searchBy(int id);

    List<T> getAll();
}

