package com.pluralsight.NorthwindTradersAPI.controller;

import com.pluralsight.NorthwindTradersAPI.dao.Dao;
import com.pluralsight.NorthwindTradersAPI.model.Category;
import com.pluralsight.NorthwindTradersAPI.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    @Qualifier("jdbcCategoryDao")
    private Dao categoryDao;

    @GetMapping("/api/category")
    public List<Product> getAllCategory(){
        return categoryDao.getAll();
    }

    @GetMapping("/api/category/{id}")
    public List<Product> categoryById(@PathVariable int id){
        return categoryDao.searchBy(id);
    }

    @PostMapping("/api/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addCategory(@RequestBody Category category){
        categoryDao.add(category);
    }

    @PutMapping("/api/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@PathVariable int id, @RequestBody Category category){
        categoryDao.update(id,category);
    }

    @DeleteMapping("/api/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable int id, @RequestBody Category category){
        categoryDao.delete(id,category);
    }
}
