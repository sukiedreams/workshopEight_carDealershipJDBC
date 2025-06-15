package com.ps;

import java.util.ArrayList;
import java.util.List;

public class Dealership {

    private int dealershipId;
    private String name;
    private String address;
    private String phone;


    public Dealership( int dealershipId, String name, String address, String phone) {
        this.dealershipId = dealershipId;
        this.name = name;
        this.address = address;
        this.phone = phone;

    }

    public Dealership(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getDealershipId() {
        return dealershipId;
    }

    public void setDealershipId(int dealershipId) {
        this.dealershipId = dealershipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private VehicleDAO vehicleDAO = new VehicleDAO();

    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    public List<Vehicle> vehiclesByPrice(double minPrice, double maxPrice) {
        return vehicleDAO.getVehiclesByPrice(minPrice, maxPrice);
    }

    public List<Vehicle> vehiclesByMakeModel(String make, String model) {
        return vehicleDAO.getVehiclesByMakeModel(make, model);
    }

    public List<Vehicle> vehiclesByYear(int minYear, int maxYear) {
        return vehicleDAO.getVehiclesByYear(minYear, maxYear);
    }

    public List<Vehicle> vehiclesByColor(String color) {
        return vehicleDAO.getVehiclesByColor(color);
    }

    public List<Vehicle> vehiclesByType(String type) {
        return vehicleDAO.getVehiclesByType(type);
    }

    public List<Vehicle> vehiclesByMileage(int minMileage, int maxMileage) {
        return vehicleDAO.getVehiclesByMileage(minMileage, maxMileage);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
    }

    public void removeVehicle(String vin) {
        vehicleDAO.removeVehicle(vin);
    }

    public Vehicle findVehicleByVin(String vin) {
        return vehicleDAO.getVehicleByVin(vin);
    }

    @Override
    public String toString() {
        return "Dealership{" +
                "dealershipId=" + dealershipId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", vehicleDAO=" + vehicleDAO +
                '}';
    }
}


