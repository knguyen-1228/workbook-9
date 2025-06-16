package com.pluralsight.NorthwindTrandersSpringBoot;

import com.pluralsight.NorthwindTrandersSpringBoot.Dao.ProductDao;
import com.pluralsight.NorthwindTrandersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindApplication implements CommandLineRunner {

    @Autowired
    @Qualifier("jdbcProductDao")
    private ProductDao productDao;

    @Override
    public void run(String... args) throws Exception {

        Scanner myScanner = new Scanner(System.in);

        while (true){
            System.out.println("\n === Product Admin Menu===");
            System.out.println("\t1) List Products");
            System.out.println("\t2) Add new Product");
            System.out.println("\t3) Update Product");
            System.out.println("\t4) Delete Product");
            System.out.println("\t5) Search by Product Name");
            System.out.println("\t0) Exit");
            System.out.println("Enter your choice: ");

            int choice = myScanner.nextInt();
            myScanner.nextLine();

            switch(choice){
                case 1:
                    List<Product> products = productDao.getAll();

                    System.out.println("Products:\t");
                    for(Product product: products){
                        System.out.println(product);
                    }
                    break;
                case 2:
                    System.out.println("Enter Product Name: ");
                    String name = myScanner.nextLine();

                    System.out.println("Enter the Category ID: ");
                    int category = Integer.parseInt(myScanner.nextLine());

                    System.out.println("Enter the Price: ");
                    double price = Double.parseDouble(myScanner.nextLine());

                    Product newProduct = new Product();
                    newProduct.setName(name);
                    newProduct.setCategory(category);
                    newProduct.setPrice(price);

                    productDao.add(newProduct);
                    break;
                case 3:
                    System.out.println("Enter the Product ID of the product to update");
                    int updateId = myScanner.nextInt();
                    myScanner.nextLine();

                    System.out.println("Enter the new Product Name:  ");
                    String updatedName = myScanner.nextLine();

                    System.out.println("Enter the new Category ID:  ");
                    int updatedCategory = myScanner.nextInt();
                    myScanner.nextLine();

                    System.out.println("Enter the new Price:  ");
                    double updatedPrice = myScanner.nextDouble();
                    myScanner.nextLine();

                    Product updatedProduct = new Product();
                    updatedProduct.setProductId(updateId);
                    updatedProduct.setName(updatedName);
                    updatedProduct.setCategory(updatedCategory);
                    updatedProduct.setPrice(updatedPrice);

                    productDao.update(updatedProduct);
                    System.out.println("Product updated!");
                    break;
                case 4:
                    System.out.println("Enter the Product ID you would like to delete:  ");
                    int deleteProduct = myScanner.nextInt();
                    myScanner.nextLine();

                    Product deletedProduct = new Product();
                    deletedProduct.setProductId(deleteProduct);

                    productDao.delete(deletedProduct);
                    break;
                case 5:
                    System.out.println("Enter a Product Name you would like to search");
                    String searchName = myScanner.nextLine();

                    Product searchProduct = new Product();
                    searchProduct.setName(searchName);

                    productDao.searchBy(searchProduct);
                    break;
                case 0:
                    System.out.println("GoodBye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, Try again!");
                    break;
            }
        }

    }
}
