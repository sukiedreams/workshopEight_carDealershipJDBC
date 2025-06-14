package com.ps;

public class Vehicle {

    private String vin;
    private String make;
    private String model;
    private int year;
    private String color;
    private int odometer;
    private double price;
    private String vehicleType;

    public Vehicle(String vin, String make, String model, int year, String color, int odometer, double price, String vehicleType) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
        this.vehicleType = vehicleType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%s,%s,%s,%s,%d,%.2f\n",
                this.vin,
                this.make,
                this.model,
                this.year,
                this.color,
                this.odometer,
                this.price,
                this.vehicleType
        );
    }
}
