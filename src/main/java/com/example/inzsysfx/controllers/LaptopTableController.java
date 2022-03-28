package com.example.inzsysfx.controllers;

import com.example.inzsysfx.entities.LaptopTableData;
import com.example.inzsysfx.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LaptopTableController implements Initializable {

    @FXML private Button importFromTxtButton;

    @FXML private TableView<LaptopTableData> laptopTable;

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

    private ObservableList<LaptopTableData> laptopDataList = FXCollections.observableArrayList();

    public LaptopTableController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void exportToXmlFile(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        fc.setTitle("Save .xml file");
        File file = fc.showSaveDialog(new Stage());

        if(file != null){
            FileUtils.saveXmlFile(file, laptopDataList);
        }
    }

    public void importFromXmlFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open .xml file");

        File file = fc.showOpenDialog((new Stage()));
        if(file != null){
            laptopDataList = FileUtils.readXmlFile(file);
            laptopTable.setItems(laptopDataList);
        }
    }

    public void importFromTxtFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open .txt file");

        File file = fc.showOpenDialog(new Stage());
        if(file != null){
            laptopDataList = FileUtils.readTxtFile(file);
            laptopTable.setItems(laptopDataList);
        }
    }

    public void exportToTxtFile(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fc.setTitle("Save .txt file");
        File file = fc.showSaveDialog(new Stage());

        if(file != null){
            FileUtils.saveTxtFile(file, laptopDataList);
        }
    }

}
