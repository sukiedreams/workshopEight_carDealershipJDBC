package com.ps;



import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, year, price, odometer, vehicle_type FROM vehicles WHERE make = ? AND model = ? AND SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapRowToVehicle(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by make & model: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, year, pricem odometer, vehicles_type FROM vehicles WHERE SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        }catch (SQLException e) {
            System.out.println("Error retrieving all vehicles: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByPrice(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, year, price, odometer, vehicle_type FROM vehicles WHERE price >= ? AND price <= ? AND SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2,maxPrice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapRowToVehicle(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by price: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByYear (int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, price, odometer, vehicle_type FROM vehicles WHERE year >= ? AND year <= ? AND SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, minYear);
            preparedStatement.setInt(2, maxYear);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapRowToVehicle(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by year: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, year, price, odometer, vehicle_type FROM vehicles WHERE color = ? AND SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, color);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapRowToVehicle(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by color: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByMileage(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, year, price, odometer, vehicle_type FROM vehicles WHERE odometer >= ? AND odometer <= ? AND SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, minMileage);
            preparedStatement.setInt(2, maxMileage);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapRowToVehicle(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by mileage: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT VIN, make, model, color, year, price, odometer, vehicle_type FROM vehicles WHERE vehicle_type = ? AND SOLD = FALSE";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapRowToVehicle(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by type: " + e.getMessage());
            e.printStackTrace();
        }

        return vehicles;
    }

    public Vehicle getVehicleByVin(String vin) {
        String sql = "SELECT VIN, make, model, color, year, price, odometer, vehicle_type FROM vehicles WHERE VIN = ?";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vin);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return mapRowToVehicle(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicles by vin: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (VIN, year, make, model, color, price, odometer, vehicle_type, SOLD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, String.valueOf(vehicle.getVin()));
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getColor());
            preparedStatement.setDouble(6, vehicle.getPrice());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setString(8, vehicle.getVehicleType());
            preparedStatement.setBoolean(9, false);
            preparedStatement.executeUpdate();
            System.out.println("Vehicle added successfully to the database.");
        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeVehicle(String vin) {
        String sql = "DELETE FROM vehicles WHERE VIN = ?";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vin);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vehicle removed form the database!");
            } else {
                System.out.println("No vehicle found with VIN:" + vin);
            }
        } catch (SQLException e) {
            System.out.println("Error removing vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void markVehicleAsSold(String vin) {
        String sql = "UPDATE vehicles SET SOLD = TRUE WHERE VIN = ?";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();
            System.out.println("Vehicle marked as sold in the database.");

        } catch (SQLException e) {
            System.out.println("Error marking vehicle as sold: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Vehicle mapRowToVehicle(ResultSet resultSet) throws SQLException {
        String vin = resultSet.getString("VIN");
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        int year = resultSet.getInt("year");
        String color = resultSet.getString("color");
        int odometer = resultSet.getInt("odometer");
        Double price = resultSet.getDouble("price");
        String vehicleType = resultSet.getString("vehicle_type");

        return new Vehicle(vin, make, model, year, color, odometer, price, vehicleType);
    }
}