package com.ps;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
//    private static BasicDataSource dataSource;
//
//    static {
//        dataSource = new BasicDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdatabase");
//        dataSource.setUsername("root");
//        dataSource.setPassword("password");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//    }
//
//    private Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//    public List<Vehicle> getByPriceRange(double min, double max) {
//        List<Vehicle> vehicles = new ArrayList<>();
//        String sql = "SELECT * FROM inventory WHERE price BETWEEN ? AND ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setDouble(1, min);
//            statement.setDouble(2, max);
//
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                vehicles.add(mapRowToVehicle(resultSet));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return vehicles;
//    }
//
//    public  void addVehicle(Vehicle vehicle) {
//        String sql = "INSERT INTO inventory (vin, make, model, year, color, odometer, price, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection connection = getConnection();
//        PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, vehicle.getVin());
//            statement.setString(2, vehicle.getMake());
//            statement.setString(3, vehicle.Model());
//            statement.setInt(4, vehicle.getYear());
//            statement.setString(5, vehicle.getColor());
//            statement.setInt(6, vehicle.getOdometer());
//            statement.setDouble(7, vehicle.getPrice());
//            statement.setString(8, vehicle.getType());
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeVehicle(String vin) {
//        String sql = "DELETE FROM inventory WHERE vin = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, vin);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Vehicle mapRowToVehicle(ResultSet resultSet) throws SQLException {
//        Vehicle vehicle = new Vehicle();
//        vehicle.setVin(resultSet.getString("vin"));
//        vehicle.setMake(resultSet.getString("make"));
//        vehicle.setModel(resultSet.getString("model"));
//        vehicle.setYears(resultSet.getInt("year"));
//        vehicle.setColor(resultSet.getString("color"));
//        vehicle.setOdometer(resultSet.getInt("odometer"));
//        vehicle.setPrice(resultSet.getDouble("price"));
//        vehicle.setType(resultSet.getString("type"));
//
//        return vehicle;
//
//    }
    public List<Vehicle> getAllVehiclesByDealership(int dealershipId) {
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = """
                SELECT v.* FROM vehicles v
                JOIN inventory i ON v.VIN = i.VIN
                WHERE i.dealership_id = ?""";

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles: " + e.getMessage());
        }

        return vehicles;
    }

    private Vehicle mapRowToVehicle(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();

        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setVehicleType(resultSet.getString("vehicle_type"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;

    }
}
