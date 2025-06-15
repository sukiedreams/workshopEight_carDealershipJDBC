package com.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesContractDAO {

    public void saveSalesContract(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (VIN, sale_date, buyer_name, sale_price)  VALUES (?, ?, ?, ?)";

        try (Connection connection = DealershipDAO.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, contract.getVehicle().getVin());
            preparedStatement.setString(2, contract.getDate());
            preparedStatement.setString(3, contract.getCustomerName());
            preparedStatement.setDouble(4, contract.getTotalPrice());
            preparedStatement.executeUpdate();
            System.out.println("Sales contract saved in the database!");

            VehicleDAO vehicleDAO = new VehicleDAO();
            vehicleDAO.markVehicleAsSold(contract.getVehicle().getVin());

        } catch (SQLException e) {
            System.out.println("Error saving sales contract: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
