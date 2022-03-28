package com.example.inzsysfx.utils;

import java.sql.*;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class DatabaseUtils {

    private static final String JDBC_Driver2 = "com.mysql.cj.jdbc.Driver"; // Xampp MariaDB

    private static Connection conn = null;

    private static final String connStr2 = "jdbc:mysql://127.0.0.1:3306/laptops";


    // Connect to DB (Maria DB on Xampp)
    public static void dbConnectXampp(){
        try {
            Class.forName(JDBC_Driver2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection(connStr2, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close connection to DB
    public static void dbDisconnect() throws SQLException{
        try{
            if(conn != null && !(conn.isClosed()))
                conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ResultSet dbExecuteQuery(String query) throws SQLException, ClassNotFoundException{
        // Declare statement, resultSet, cachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs = null;

        try{
            // Connect to DB
            dbConnectXampp();
            System.out.println("Select statement: " + query + "\n");

            // Create statement
            stmt = conn.createStatement();

            // Execute query
            resultSet = stmt.executeQuery(query);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(resultSet);
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                // Close resultSet
                resultSet.close();
            }
            if(stmt != null){
                // Close statement
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }

    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnectXampp();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
}
