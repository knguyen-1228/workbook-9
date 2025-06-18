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
public class ProductController {

    @Autowired
    @Qualifier("jdbcProductDao")
    private Dao productDao;

    @GetMapping("/api/product")
    public List<Product> getAllProducts(){
        return productDao.getAll();
    }

    @GetMapping("/api/product/{id}")
    public List<Product> productById(@PathVariable int id){
        return productDao.searchBy(id);
    }

    @PostMapping("/api/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addProduct(@RequestBody Product product){
        productDao.add(product);
    }

    @PutMapping("/api/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable int id, @RequestBody Product product){
        productDao.update(id,product);
    }

    @DeleteMapping("/api/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id, @RequestBody Product product){
        productDao.delete(id,product);
    }

}
