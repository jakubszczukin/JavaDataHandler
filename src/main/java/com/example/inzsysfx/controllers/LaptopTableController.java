package com.example.inzsysfx.controllers;

import com.example.inzsysfx.dao.LaptopDAO;
import com.example.inzsysfx.entities.LaptopTableData;
import com.example.inzsysfx.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

public class LaptopTableController implements Initializable {

    @FXML private TableView<LaptopTableData> laptopTable;

    @FXML private TableColumn<LaptopTableData, Integer> idColumn;

    @FXML private TableColumn<LaptopTableData, String> manufacturerColumn;

    @FXML private TableColumn<LaptopTableData, String> diagonalColumn;

    @FXML private TableColumn<LaptopTableData, String> resolutionColumn;

    @FXML private TableColumn<LaptopTableData, String> matrixTypeColumn;

    @FXML private TableColumn<LaptopTableData, String> isTouchscreenColumn;

    @FXML private TableColumn<LaptopTableData, String> cpuModelColumn;

    @FXML private TableColumn<LaptopTableData, Long> cpuCoresColumn;

    @FXML private TableColumn<LaptopTableData, Long> cpuClockSpeedColumn;

    @FXML private TableColumn<LaptopTableData, String> ramColumn;

    @FXML private TableColumn<LaptopTableData, String> driveCapacityColumn;

    @FXML private TableColumn<LaptopTableData, String> driveTypeColumn;

    @FXML private TableColumn<LaptopTableData, String> gpuModelColumn;

    @FXML private TableColumn<LaptopTableData, String> gpuMemoryColumn;

    @FXML private TableColumn<LaptopTableData, String> operatingSystemColumn;

    @FXML private TableColumn<LaptopTableData, String> opticalDriveTypeColumn;

    @FXML private Text sourceText;

    private ObservableList<LaptopTableData> laptopDataList = FXCollections.observableArrayList();

    public LaptopTableController() {}

    enum Status{
        UPDATED,
        NOT_UPDATED,
        NOT_EXISTS
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        diagonalColumn.setCellValueFactory(new PropertyValueFactory<>("diagonal"));
        resolutionColumn.setCellValueFactory(new PropertyValueFactory<>("resolution"));
        matrixTypeColumn.setCellValueFactory(new PropertyValueFactory<>("matrixType"));
        isTouchscreenColumn.setCellValueFactory(new PropertyValueFactory<>("isTouchscreen"));
        cpuModelColumn.setCellValueFactory(new PropertyValueFactory<>("cpuModel"));
        cpuCoresColumn.setCellValueFactory(new PropertyValueFactory<>("cpuCores"));
        cpuClockSpeedColumn.setCellValueFactory(new PropertyValueFactory<>("cpuClockSpeed"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("ram"));
        driveCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("driveCapacity"));
        driveTypeColumn.setCellValueFactory(new PropertyValueFactory<>("driveType"));
        gpuModelColumn.setCellValueFactory(new PropertyValueFactory<>("gpuModel"));
        gpuMemoryColumn.setCellValueFactory(new PropertyValueFactory<>("gpuMemory"));
        operatingSystemColumn.setCellValueFactory(new PropertyValueFactory<>("operatingSystem"));
        opticalDriveTypeColumn.setCellValueFactory(new PropertyValueFactory<>("opticalDriveType"));

        laptopTable.setEditable(true);
        laptopTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
        idColumn.setEditable(false);

        // Set a custom cel lfactory for column to change row background
        laptopTable.setRowFactory(tr -> new TableRow<>() {
            @Override
            public void updateItem(LaptopTableData item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getDuplicated() == null)
                    setStyle("");
                else if (item.getDuplicated()) {
                    setStyle("-fx-background-color: red");
                    System.out.println(item.getLaptopDataAsString() + item.getDuplicated().toString());
                }
                else if(!item.getDuplicated()) {
                    System.out.println(item.getLaptopDataAsString() + item.getDuplicated().toString());
                    setStyle("-fx-background-color: gray");
                }
            }
        });

        /*
        // Custom TableCell class to change style whenever cell is updated
        manufacturerColumn.setCellFactory(new Callback<TableColumn<LaptopTableData, String>, TableCell<LaptopTableData, String>>() {
            @Override
            public TableCell<LaptopTableData, String> call(TableColumn<LaptopTableData, String> laptopTableDataStringTableColumn) {
                return new TextFieldTableCell<LaptopTableData, String>(new DefaultStringConverter()){
                    @Override public void updateItem(final String item, final boolean empty){
                        super.updateItem(item, empty);

                        // clear any custom styles
                        this.getStyleClass().remove("validationFail");

                        //update the item and set a custom style if necessary

                        if(item != null){
                            if(item.equals("")){
                                this.getStyleClass().add("validationFail");
                            }
                            else
                                setText(item);
                        }
                    }
                };
            }
        });
        */

        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        manufacturerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        diagonalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resolutionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        matrixTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isTouchscreenColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cpuModelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cpuCoresColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        cpuClockSpeedColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        ramColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        driveCapacityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        driveTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gpuModelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gpuMemoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        operatingSystemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        opticalDriveTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        manufacturerColumn.setOnEditCommit(e -> {
            if(!e.getNewValue().equals("")){
                LaptopTableData tempLap = e.getRowValue();
                tempLap.setManufacturer(e.getNewValue());
            } else{
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("Wrong manufacturer!");
              alert.setContentText("Manufacturer cannot be left empty!");
              alert.showAndWait();
            }
            laptopTable.refresh();
        });

        cpuCoresColumn.setOnEditCommit(e ->{
           if(e.getNewValue() == null){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Wrong number of CPU cores!");
               alert.setContentText("CPU Cores cannot be left empty!");
               alert.showAndWait();
           }
           else if(e.getNewValue() < 0){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Wrong number of CPU cores!");
               alert.setContentText("CPU Cores value cannot be negative.");
               alert.showAndWait();
           }
           else{
                LaptopTableData tempLap = e.getRowValue();
                tempLap.setCpuCores(e.getNewValue());
           }
           laptopTable.refresh();
        });

    }

    public Status checkIfUpdated(LaptopTableData laptop) throws SQLException, ClassNotFoundException {
        LaptopTableData compLaptop = LaptopDAO.searchLaptop(Integer.toString(laptop.getId()));
        if(compLaptop == null)
            return Status.NOT_EXISTS;
        if(laptop.getLaptopDataAsString().equals(compLaptop.getLaptopDataAsString()))
            return Status.NOT_UPDATED;
        else
            return Status.UPDATED;
    }

    public int findDuplicates(ObservableList<LaptopTableData> lapList){
        ObservableList<LaptopTableData> lapTableData = laptopTable.getItems();
        //ArrayList<LaptopTableData> foundDuplicates = new ArrayList<>();
        int dupAmount = 0;
        for(LaptopTableData laptop : lapList){
            for(LaptopTableData tableLaptop : lapTableData){
                if(laptop.getLaptopDataAsString().equals(tableLaptop.getLaptopDataAsString())){
                    laptop.setDuplicated(true);
                    dupAmount++;
                    System.out.println(laptop.getLaptopDataAsString() + laptop.getDuplicated().toString());
                }
                else if(!laptop.getLaptopDataAsString().equals(tableLaptop.getLaptopDataAsString()) && laptop.getDuplicated() == null){
                    laptop.setDuplicated(false);
                }
            }
        }
        return dupAmount;
    }

    public void importFromDatabase() throws SQLException, ClassNotFoundException {
        ObservableList<LaptopTableData> lapList;
        lapList = LaptopDAO.returnAllLaptops();
       // ArrayList<LaptopTableData> dupLapList = findDuplicates(lapList);
        int dupAmount = findDuplicates(lapList);
        sourceText.setText("Data imported from database. Found " + dupAmount + " duplicated records");
        laptopTable.setItems(lapList);
    }

    public void exportToDatabase() throws SQLException, ClassNotFoundException {
        ObservableList<LaptopTableData> tableList = laptopTable.getItems();
        ArrayList<LaptopTableData> listToSave = new ArrayList<>();
        int updRecAmount = 0;
        for(LaptopTableData laptop : tableList){
            Status laptopStatus = checkIfUpdated(laptop);
            if(laptopStatus == Status.NOT_EXISTS){
                System.out.println(laptop.getId() + ";" + laptop.getLaptopDataAsString());
                listToSave.add(laptop);
            }
            else if(laptopStatus == Status.UPDATED){
                LaptopDAO.updateLaptop(laptop);
                updRecAmount++;
            }
        }
        System.out.println("TableListSize = " + tableList.size() + ", listToSaveSize = " + listToSave.size());
        LaptopDAO.insertLaptopList(listToSave);
        sourceText.setText("Data exported to database. Updated " + updRecAmount + " records, added " + listToSave.size() + " records.");
    }

    public void exportToXmlFile(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        fc.setTitle("Save .xml file");
        File file = fc.showSaveDialog(new Stage());

        if(file != null){
            FileUtils.saveXmlFile(file, laptopDataList);
            sourceText.setText("Data exported to xml file.");
        }
    }

    public void importFromXmlFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open .xml file");

        File file = fc.showOpenDialog((new Stage()));
        if(file != null){
            laptopDataList = FileUtils.readXmlFile(file);
            //ArrayList<LaptopTableData> dupLapList = findDuplicates(laptopDataList);
            int dupAmount = findDuplicates(laptopDataList);
            laptopTable.setItems(laptopDataList);
            sourceText.setText("Data imported from xml file. Found " + dupAmount + " duplicated records");
        }
    }

    public void importFromTxtFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open .txt file");

        File file = fc.showOpenDialog(new Stage());
        if(file != null){
            laptopDataList = FileUtils.readTxtFile(file);
            //ArrayList<LaptopTableData> dupLapList = findDuplicates(laptopDataList);
            int dupAmount = findDuplicates(laptopDataList);
            laptopTable.setItems(laptopDataList);
            sourceText.setText("Data imported from txt file. Found " + dupAmount + " duplicated records");
        }
    }

    public void exportToTxtFile(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fc.setTitle("Save .txt file");
        File file = fc.showSaveDialog(new Stage());

        if(file != null){
            FileUtils.saveTxtFile(file, laptopDataList);
            sourceText.setText("Data exported to txt file.");
        }
    }

}
