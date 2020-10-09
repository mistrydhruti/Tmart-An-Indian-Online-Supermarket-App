package com.example.magicalwinds.Model;

public class ProductModel {

    private String Category,Description,Image,Pname,Savings,date,pid,price,time,Mrp,Quantity;

    public ProductModel()
    {

    }

    public ProductModel(String category, String description, String image, String pname,
                        String savings, String date, String pid, String price, String time,
                        String mrp, String quantity) {
        Category = category;
        Description = description;
        Image = image;
        Pname = pname;
        Savings = savings;
        this.date = date;
        this.pid = pid;
        this.price = price;
        this.time = time;
        Mrp = mrp;
        Quantity = quantity;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getSavings() {
        return Savings;
    }

    public void setSavings(String savings) {
        Savings = savings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMrp() {
        return Mrp;
    }

    public void setMrp(String mrp) {
        Mrp = mrp;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
