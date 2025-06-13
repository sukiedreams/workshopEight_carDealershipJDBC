package com.ps;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    // aka database connection.
    private static  final BasicDataSource dataSource = new BasicDataSource();

    static {
            dataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdatabase");
            dataSource.setUsername("root");
            dataSource.setPassword("password");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
