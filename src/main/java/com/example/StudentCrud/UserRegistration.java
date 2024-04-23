//To register and login users using singleton pattern

package com.example.StudentCrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRegistration {
    private static UserRegistration instance;
    private Connection connection;

    private UserRegistration() {
        try {
            // Establishing database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfound", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserRegistration getInstance() {
        if (instance == null) {
            instance = new UserRegistration();
        }
        return instance;
    }

    public void registerUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student (username, password) VALUES (?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a record with given username and password exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRegistration userRegistration = UserRegistration.getInstance();

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.nextLine();
                    userRegistration.registerUser(regUsername, regPassword);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    if (userRegistration.loginUser(loginUsername, loginPassword)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    try {
                        userRegistration.connection.close(); // Closing database connection
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please enter again.");
            }
        }
    }
}

