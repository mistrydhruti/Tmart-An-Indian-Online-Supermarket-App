package com.example.magicalwinds.Model;

public class Cart {
    private String Pname,amount,category,date,price,quantity,time,pid,image,mrp;

    public Cart()
    {

    }

    public Cart(String pname, String amount, String category, String date, String price, String quantity, String time, String pid, String image, String mrp) {
        Pname = pname;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.pid = pid;
        this.image = image;
        this.mrp = mrp;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }
}
