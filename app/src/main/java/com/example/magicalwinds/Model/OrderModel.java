package com.example.magicalwinds.Model;

public class OrderModel {

    private String Address,Email,Name,Payment,ExTime,Totalamount,OrderId,OrderedDate,OrderedTime,UniqueNo,state;

    public OrderModel()
    {

    }

    public OrderModel(String address, String email, String name, String payment, String exTime, String totalamount, String orderId, String orderedDate, String orderedTime, String uniqueNo, String state) {
        Address = address;
        Email = email;
        Name = name;
        Payment = payment;
        ExTime = exTime;
        Totalamount = totalamount;
        OrderId = orderId;
        OrderedDate = orderedDate;
        OrderedTime = orderedTime;
        UniqueNo = uniqueNo;
        this.state = state;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getExTime() {
        return ExTime;
    }

    public void setExTime(String exTime) {
        ExTime = exTime;
    }

    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderedDate() {
        return OrderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        OrderedDate = orderedDate;
    }

    public String getOrderedTime() {
        return OrderedTime;
    }

    public void setOrderedTime(String orderedTime) {
        OrderedTime = orderedTime;
    }

    public String getUniqueNo() {
        return UniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        UniqueNo = uniqueNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
