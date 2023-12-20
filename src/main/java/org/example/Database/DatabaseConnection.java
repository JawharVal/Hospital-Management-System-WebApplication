package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hospitaal";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);


            String sqlDepartments = "CREATE TABLE IF NOT EXISTS departments (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "number_of_patients INT" +
                    ");";

            String sqlPatients = "CREATE TABLE IF NOT EXISTS patients (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "fullName VARCHAR(255), " +
                    "age INT, " +
                    "gender VARCHAR(255), " +
                    "department_id INT, " +
                    "FOREIGN KEY (department_id) REFERENCES departments(id)" +
                    ");";

            Statement stmt = connection.createStatement();
            stmt.execute(sqlDepartments);
            stmt.execute(sqlPatients);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
