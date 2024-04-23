//To list all the lost items using adapter pattern

package com.example.StudentCrud;

import java.sql.*;

// Interface for the adapter
interface ItemQuery {
    void executeQuery();
}

// Concrete adapter class implementing ItemQuery interface
class NotNullItemsAdapter implements ItemQuery {
    private DataQuery dataQuery;

    // Constructor to initialize the dataQuery object
    public NotNullItemsAdapter(DataQuery dataQuery) {
        this.dataQuery = dataQuery;
    }

    // Execute the adapted query
    @Override
    public void executeQuery() {
        dataQuery.executeQuery();
    }
}

// Original database query class
abstract class DataQuery {
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

// Concrete implementation of DataQuery to list not null items
class NotNullItemsQuery extends DataQuery {
    // Concrete implementation of getQuery method
    @Override
    protected String getQuery() {
        return "SELECT studentname, itemname, lost_item, contactnumber FROM student WHERE studentname IS NOT NULL AND lost_item IS NOT NULL AND lost_item LIKE 1";
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
            		" - Item Name: " + itemname +
                    " - Lost Item: " + lost_item +
                    " - Contact Number: " + contactnumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class AdapterPattern {
    public static void main(String[] args) {
        // Create the original query object
        NotNullItemsQuery originalQuery = new NotNullItemsQuery();
        
        // Create the adapter with the original query object
        NotNullItemsAdapter adapter = new NotNullItemsAdapter(originalQuery);
        
        // Execute the adapted query
        adapter.executeQuery();
    }
}
