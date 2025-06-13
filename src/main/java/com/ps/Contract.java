package com.ps;

public abstract class Contract {

    private String date;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicle;

    public Contract (String date, String customerName, String customerEmail, Vehicle vehicle) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicle = vehicle;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}

