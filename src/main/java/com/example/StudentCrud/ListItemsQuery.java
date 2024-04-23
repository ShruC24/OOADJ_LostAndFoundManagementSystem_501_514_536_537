//To list all found items using template pattern

package com.example.StudentCrud;

import java.sql.*;

abstract class DatabaseQuery {
    // Template method for executing a database query
    public void executeQuery() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfound", "root", "");

            // Create statement
            statement = connection.createStatement();

            // Execute query
            resultSet = statement.executeQuery(getQuery());

            // Process result set
            while (resultSet.next()) {
                processRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Abstract method for getting the SQL query
    protected abstract String getQuery();

    // Abstract method for processing each row of the result set
    protected abstract void processRow(ResultSet resultSet);
}

public class ListItemsQuery extends DatabaseQuery {
    // Concrete implementation of getQuery method
    @Override
    protected String getQuery() {
        return "SELECT studentname, itemname, lost_item, contactnumber FROM student WHERE itemname IS NOT NULL AND lost_item like 0";
    }

    // Concrete implementation of processRow method
    @Override
    protected void processRow(ResultSet resultSet) {
        try {
            String studentname = resultSet.getString("studentname");
            String itemname = resultSet.getString("itemname");
            String lost_item = resultSet.getString("lost_item");
            String contactnumber = resultSet.getString("contactnumber");

            // Display the required columns
            System.out.println("Student Name: " + studentname +
                    " - Found Item Name: " + itemname +
                    " - Contact Number: " + contactnumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create and execute the query
        ListItemsQuery query = new ListItemsQuery();
        query.executeQuery();
    }
}