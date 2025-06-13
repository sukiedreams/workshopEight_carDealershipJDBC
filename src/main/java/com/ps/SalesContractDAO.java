package com.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesContractDAO {

    public void saveSalesContract(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (VIN, sale_date, buyer_name, sale_price)  VALUES (?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contract.getVehicle().getVin());
            statement.setString(2, contract.getDate());
            statement.setString(3, contract.getCustomerName());
            statement.setDouble(4, contract.getTotalPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving sales contract: " + e.getMessage());
        }
    }
}
