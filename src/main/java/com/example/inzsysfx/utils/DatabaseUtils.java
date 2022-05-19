package com.example.inzsysfx.utils;

import com.example.inzsysfx.entities.LaptopTableData;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

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

    public static void dbUpdateLaptopDatabase(ObservableList<LaptopTableData> lapData) throws SQLException {
        try{
            dbConnectXampp();
            String sql = "INSERT INTO laptops(manufacturer, diagonal, resolution, matrixType, isTouchscreen, cpuModel, cpuCores, cpuClockSpeed, ram, driveCapacity, driveType, gpuModel, gpuMemory, operatingSystem, opticalDriveType) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            for(LaptopTableData laptop : lapData){
                ps.clearParameters();
                ps.setString(1, laptop.getManufacturer());
                ps.setString(2, laptop.getDiagonal());
                ps.setString(3, laptop.getResolution());
                ps.setString(4, laptop.getMatrixType());
                ps.setString(5, laptop.getIsTouchscreen());
                ps.setString(6, laptop.getCpuModel());
                ps.setLong(7, laptop.getCpuCores());
                ps.setLong(8, laptop.getCpuClockSpeed());
                ps.setString(9, laptop.getRam());
                ps.setString(10, laptop.getDriveCapacity());
                ps.setString(11, laptop.getDriveType());
                ps.setString(12, laptop.getGpuModel());
                ps.setString(13, laptop.getGpuMemory());
                ps.setString(14, laptop.getOperatingSystem());
                ps.setString(15, laptop.getOpticalDriveType());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        }
        dbDisconnect();
    }
}
