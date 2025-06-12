package com.ps;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class VehicleDAO {
    private static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdatabase");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public list<Vehicle> getByPriceRange(double min, double max) {

    }

}
