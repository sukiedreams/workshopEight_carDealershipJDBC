package com.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LeaseContractsDAO {

    public void saveLeaseContract(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (VIN, lease_start, lease_end, monthly_payment) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            LocalDate startDate = LocalDate.parse(contract.getDate());
            LocalDate endDate = startDate.plusYears(2);

            statement.setString(1, contract.getVehicle().getVin());
            statement.setString(2, startDate.toString());
            statement.setString(3, endDate.toString());
            statement.setDouble(4, contract.getMonthlyPayment());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving lease contract: " + e.getMessage());
        }
    }
}
