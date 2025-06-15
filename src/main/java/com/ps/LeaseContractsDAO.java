package com.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LeaseContractsDAO {

    public void saveLeaseContract(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (VIN, lease_start, lease_end, monthly_payment) VALUES (?, ?, ?, ?)";

        try (Connection connection = DealershipDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            LocalDate startDate = LocalDate.parse(contract.getDate());
            LocalDate endDate = startDate.plusYears(2);

            preparedStatement.setString(1, contract.getVehicle().getVin());
            preparedStatement.setString(2, startDate.toString());
            preparedStatement.setString(3, endDate.toString());
            preparedStatement.setDouble(4, contract.getMonthlyPayment());
            preparedStatement.executeUpdate();
            System.out.println("Lease contract saved in the database!");

            VehicleDAO vehicleDAO = new VehicleDAO();
            vehicleDAO.markVehicleAsSold(contract.getVehicle().getVin());

        } catch (SQLException e) {
            System.out.println("Error saving lease contract: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
