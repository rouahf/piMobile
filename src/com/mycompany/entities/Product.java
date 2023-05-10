/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author MSI
 */
public class Product {
    private int id;
    private String nameProduct;
    private category category_id ;
    private String description;
    private float price;
    private String image;
    private int quantity;

    public Product(int id, String nameProduct, category category_id, String description, float price, String image, int quantity) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.category_id = category_id;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public Product(String nameProduct, category category_id, String description, float price, String image, int quantity) {
        this.nameProduct = nameProduct;
        this.category_id = category_id;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public category getCategory_id() {
        return category_id;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setCategory_id(category category_id) {
        this.category_id = category_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
