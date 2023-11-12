//package com.example.catalogueserver.service;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabaseConnection {
//
//    private final DataSource dataSource;
//
//    @Autowired
//    public DatabaseConnection(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public Connection connect() {
//        try {
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            // Log the exception and handle it appropriately
//            System.out.println("Error while getting database connection: " + e.getMessage());
//            return null;
//        }
//    }
//}
