package com.example.inzsysfx.dao;

import com.example.inzsysfx.entities.LaptopTableData;
import com.example.inzsysfx.utils.DatabaseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LaptopDAO {

    public static LaptopTableData searchLaptop(String laptopId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Laptops WHERE laptop_id=" + laptopId;

        try{
            ResultSet laptopResult = DatabaseUtils.dbExecuteQuery(query);
            LaptopTableData laptop = getLaptopFromResultSet(laptopResult);
            return laptop;
        } catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    // Use ResultSet from Database as parameter, set Laptop Object's attributes and return laptop object.
    private static LaptopTableData getLaptopFromResultSet(ResultSet rs) throws SQLException{
        try{
            LaptopTableData lap = null;
            if(rs.next()){
                lap = new LaptopTableData();
                lap.setManufacturer(rs.getString("manufacturer"));
                lap.setDiagonal(rs.getString("diagonal"));
                lap.setResolution(rs.getString("resolution"));
                lap.setMatrixType(rs.getString("matrixType"));
                lap.setIsTouchscreen(rs.getString("isTouchScreen"));
                lap.setCpuModel(rs.getString("cpuModel"));
                lap.setCpuCores(rs.getLong("cpuCores"));
                lap.setCpuClockSpeed(rs.getLong("cpuClockSpeed"));
                lap.setRam(rs.getString("ram"));
                lap.setDriveCapacity(rs.getString("driveCapacity"));
                lap.setDriveType(rs.getString("driveType"));
                lap.setGpuModel(rs.getString("gpuModel"));
                lap.setGpuMemory(rs.getString("gpuMemory"));
                lap.setOperatingSystem(rs.getString("operatingSystem"));
                lap.setOpticalDriveType(rs.getString("opticalDriveType"));
            }
            return lap;
        } catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    //*******************************
    //SELECT Laptops
    //*******************************
    public static ObservableList<LaptopTableData> returnAllLaptops () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM laptops";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DatabaseUtils.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<LaptopTableData> empList = getLaptopList(rsEmps);
            //Return employee object
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from laptops operation
    private static ObservableList<LaptopTableData> getLaptopList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Laptops objects
        ObservableList<LaptopTableData> lapList = FXCollections.observableArrayList();
        while (rs.next()) {
            LaptopTableData lap = new LaptopTableData();
            lap.setManufacturer(rs.getString("manufacturer"));
            lap.setDiagonal(rs.getString("diagonal"));
            lap.setResolution(rs.getString("resolution"));
            lap.setMatrixType(rs.getString("matrixType"));
            lap.setIsTouchscreen(rs.getString("isTouchScreen"));
            lap.setCpuModel(rs.getString("cpuModel"));
            lap.setCpuCores(rs.getLong("cpuCores"));
            lap.setCpuClockSpeed(rs.getLong("cpuClockSpeed"));
            lap.setRam(rs.getString("ram"));
            lap.setDriveCapacity(rs.getString("driveCapacity"));
            lap.setDriveType(rs.getString("driveType"));
            lap.setGpuModel(rs.getString("gpuModel"));
            lap.setGpuMemory(rs.getString("gpuMemory"));
            lap.setOperatingSystem(rs.getString("operatingSystem"));
            lap.setOpticalDriveType(rs.getString("opticalDriveType"));
            //Add laptop to the ObservableList
            lapList.add(lap);
        }
        //return empList (ObservableList of Laptops)
        return lapList;
    }

    //*************************************
    //INSERT an laptop
    //*************************************
    public static void insertLaptop (String manufacturer, String diagonal, String resolution, String matrixType, String isTouchscreen, String cpuModel, Long cpuCores, Long cpuClockSpeed, String ram, String driveCapacity, String driveType, String gpuModel, String gpuMemory, String operatingSystem, String opticalDriveType) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String insertQuery =
                "BEGIN\n" +
                        "INSERT INTO laptops\n" +
                        "(manufacturer, diagonal, resolution, matrixType, isTouchScreen, cpuModel, cpuCores, cpuClockSpeed, ram, driveCapacity, driveType, gpuModel, gpuMemory, operatingSystem, opticalDriveType)\n" +
                        "VALUES\n" +
                        "('" + manufacturer + "', '" + diagonal + "', '" + resolution + "', '" + matrixType + "', '" + isTouchscreen + "', '" + cpuModel + "', '" + cpuCores + "', '" + cpuClockSpeed + "', '" + ram + "', '" + driveCapacity + "', '" + driveType + "', '" + gpuModel + "', '" + operatingSystem + "', '" + opticalDriveType + "')\n" +
                        "END;";
        //Execute DELETE operation
        try {
            DatabaseUtils.dbExecuteUpdate(insertQuery);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

}
