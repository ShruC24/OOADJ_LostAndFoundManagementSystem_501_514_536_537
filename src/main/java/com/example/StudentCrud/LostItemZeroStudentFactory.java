package com.example.StudentCrud; // Make sure this matches your package name

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Import Student class
import com.example.StudentCrud.Student;

public class LostItemZeroStudentFactory {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lostandfound";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static List<Student> getStudents() {
        return getStudents(0);
    }

    private static List<Student> getStudents(int lostItemValue) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM student WHERE lost_item = ? and studentname is not null";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, lostItemValue);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String studentName = resultSet.getString("studentName");
                    String itemName = resultSet.getString("itemName");
                    Long contactNumber = resultSet.getLong("contactNumber");
                    boolean lostItem = resultSet.getInt("lost_item") == 1;

                    students.add(new Student(studentName, itemName, contactNumber, lostItem));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students from the database: " + e.getMessage());
        }

        return students;
    }

    public static void main(String[] args) {
        List<Student> lostItemZeroStudents = LostItemZeroStudentFactory.getStudents();
        System.out.println("Students with lost_item=0:");
        displayStudents(lostItemZeroStudents);
    }

    private static void displayStudents(List<Student> students) {
        for (Student student : students) {
            System.out.println(student.getStudentName() + " - " + student.getItemName() +
                    " - " + student.getContactNumber() + " - " + (student.isLostItem() ? "Lost" : "Found"));
        }
    }
}