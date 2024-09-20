package com.zakaria.finalexam.repository.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
@Getter
public class ConnectionDB {
    private final String url = "jdbc:postgresql://localhost:5432/todos";
    private final String username = "postgres";
    private final String password = "zakaria";

    private Connection connection;
    public ConnectionDB() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch(SQLException | ClassNotFoundException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}
