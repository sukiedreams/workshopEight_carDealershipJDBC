package com.ps;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DealershipDAO {

    private static BasicDataSource dataSource;

    static {

        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdatabase");
        dataSource.setUsername("root");
        dataSource.setPassword("@Kendallswife24");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void shutdown() throws SQLException {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public Dealership getDealership() {
        Dealership dealerships = null;

        String sql = "SELECT dealership_id, name address, phone FROM dealership LIMIT 1";

        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int id = resultSet.getInt("dealership_id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                dealerships = new Dealership(id, name, address, phone);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving dealership: " + e.getMessage());
            e.printStackTrace();
        }

        return dealerships;
    }

    public void saveDealership(Dealership dealerships) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM dealerships";
        String sql;

        try (Connection connection = getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             ResultSet resultSet = checkStmt.executeQuery()) {

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                sql = "UPDATE dealerships SET name = ?, address = ?, phone = ? WHERE dealership_id = (SELECT MIN(dealership_id) FROM dealerships)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    preparedStatement.setString(1, dealerships.getName());
                    preparedStatement.setString(2, dealerships.getAddress());
                    preparedStatement.setString(3, dealerships.getPhone());
                    preparedStatement.executeUpdate();
                    System.out.println("Dealership updated!");
                }
            } else {
                sql = "INSERT INTO dealerships (name, address, phone) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, dealerships.getName());
                    preparedStatement.setString(2, dealerships.getAddress());
                    preparedStatement.setString(3, dealerships.getPhone());
                    preparedStatement.executeUpdate();

                    try (ResultSet generateKeys = preparedStatement.getGeneratedKeys()){
                        if (generateKeys.next()) {
                            dealerships.setDealershipId(generateKeys.getInt(1));
                        }
                    }
                    System.out.println("Dealership saved!");
                } catch (SQLException e) {
                    System.out.println("Error saving dealership: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

}
