package com.example.inzsysfx.entities;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class LaptopTableData {

    /**
     * String/Long can be used instead of Simple variant but the latter ones are "wrappers" which can be used to add listeners or bind to other properties
     */
    private final SimpleStringProperty manufacturer;
    private final SimpleStringProperty diagonal;
    private final SimpleStringProperty resolution;
    private final SimpleStringProperty matrixType;
    private final SimpleStringProperty isTouchscreen;
    private final SimpleStringProperty cpuModel;
    private final SimpleLongProperty cpuCores;
    private final SimpleLongProperty cpuClockSpeed;
    private final SimpleStringProperty ram;
    private final SimpleStringProperty driveCapacity;
    private final SimpleStringProperty driveType;
    private final SimpleStringProperty gpuModel;
    private final SimpleStringProperty gpuMemory;
    private final SimpleStringProperty operatingSystem;
    private final SimpleStringProperty opticalDriveType;


    public LaptopTableData(String manufacturer, String diagonal, String resolution, String matrixType, String isTouchscreen, String cpuModel, Long cpuCores, Long cpuClockSpeed, String ram, String driveCapacity, String driveType, String gpuModel, String gpuMemory, String operatingSystem, String opticalDriveType) {
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.diagonal = new SimpleStringProperty(diagonal);
        this.resolution = new SimpleStringProperty(resolution);
        this.matrixType = new SimpleStringProperty(matrixType);
        this.isTouchscreen = new SimpleStringProperty(isTouchscreen);
        this.cpuModel = new SimpleStringProperty(cpuModel);
        this.cpuCores = new SimpleLongProperty(cpuCores);
        this.cpuClockSpeed = new SimpleLongProperty(cpuClockSpeed);
        this.ram = new SimpleStringProperty(ram);
        this.driveCapacity = new SimpleStringProperty(driveCapacity);
        this.driveType = new SimpleStringProperty(driveType);
        this.gpuModel = new SimpleStringProperty(gpuModel);
        this.gpuMemory = new SimpleStringProperty(gpuMemory);
        this.operatingSystem = new SimpleStringProperty(operatingSystem);
        this.opticalDriveType = new SimpleStringProperty(opticalDriveType);
    }

    public LaptopTableData(){
        this.manufacturer = new SimpleStringProperty();
        this.diagonal = new SimpleStringProperty();
        this.resolution = new SimpleStringProperty();
        this.matrixType = new SimpleStringProperty();
        this.isTouchscreen = new SimpleStringProperty();
        this.cpuModel = new SimpleStringProperty();
        this.cpuCores = new SimpleLongProperty();
        this.cpuClockSpeed = new SimpleLongProperty();
        this.ram = new SimpleStringProperty();
        this.driveCapacity = new SimpleStringProperty();
        this.driveType = new SimpleStringProperty();
        this.gpuModel = new SimpleStringProperty();
        this.gpuMemory = new SimpleStringProperty();
        this.operatingSystem = new SimpleStringProperty();
        this.opticalDriveType = new SimpleStringProperty();
    }

    public String getLaptopDataAsString(){
        return correctUnknownInfo(manufacturer.get()) + ";"
                + correctUnknownInfo(diagonal.get()) + ";"
                + correctUnknownInfo(resolution.get()) + ";"
                + correctUnknownInfo(matrixType.get()) + ";"
                + correctUnknownInfo(isTouchscreen.get()) + ";"
                + correctUnknownInfo(cpuModel.get()) + ";"
                + correctUnknownInfo(cpuCores.get()) + ";"
                + correctUnknownInfo(cpuClockSpeed.get()) + ";"
                + correctUnknownInfo(ram.get()) + ";"
                + correctUnknownInfo(driveCapacity.get()) + ";"
                + correctUnknownInfo(driveType.get()) + ";"
                + correctUnknownInfo(gpuModel.get()) + ";"
                + correctUnknownInfo(gpuMemory.get()) + ";"
                + correctUnknownInfo(operatingSystem.get()) + ";"
                + correctUnknownInfo(opticalDriveType.get()) + ";";
    }

    private String correctUnknownInfo(String str){
        return str.equals("Brak Informacji") ? "" : str;
    }

    private String correctUnknownInfo(Long lng){
        return (lng == 0) ? "" : lng.toString();
    }

    public String getGpuMemory() {
        return gpuMemory.get();
    }

    public SimpleStringProperty gpuMemoryProperty() {
        return gpuMemory;
    }

    public void setGpuMemory(String gpuMemory) {
        this.gpuMemory.set(gpuMemory);
    }

    public String getRam() {
        return ram.get();
    }

    public SimpleStringProperty ramProperty() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram.set(ram);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public SimpleStringProperty manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public String getDiagonal() {
        return diagonal.get();
    }

    public SimpleStringProperty diagonalProperty() {
        return diagonal;
    }

    public void setDiagonal(String diagonal) {
        this.diagonal.set(diagonal);
    }

    public String getResolution() {
        return resolution.get();
    }

    public SimpleStringProperty resolutionProperty() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution.set(resolution);
    }

    public String getMatrixType() {
        return matrixType.get();
    }

    public SimpleStringProperty matrixTypeProperty() {
        return matrixType;
    }

    public void setMatrixType(String matrixType) {
        this.matrixType.set(matrixType);
    }

    public String getIsTouchscreen() {
        return isTouchscreen.get();
    }

    public SimpleStringProperty isTouchscreenProperty() {
        return isTouchscreen;
    }

    public void setIsTouchscreen(String isTouchscreen) {
        this.isTouchscreen.set(isTouchscreen);
    }

    public String getCpuModel() {
        return cpuModel.get();
    }

    public SimpleStringProperty cpuModelProperty() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel.set(cpuModel);
    }

    public long getCpuCores() {
        return cpuCores.get();
    }

    public SimpleLongProperty cpuCoresProperty() {
        return cpuCores;
    }

    public void setCpuCores(long cpuCores) {
        this.cpuCores.set(cpuCores);
    }

    public long getCpuClockSpeed() {
        return cpuClockSpeed.get();
    }

    public SimpleLongProperty cpuClockSpeedProperty() {
        return cpuClockSpeed;
    }

    public void setCpuClockSpeed(long cpuClockSpeed) {
        this.cpuClockSpeed.set(cpuClockSpeed);
    }

    public String getDriveCapacity() {
        return driveCapacity.get();
    }

    public SimpleStringProperty driveCapacityProperty() {
        return driveCapacity;
    }

    public void setDriveCapacity(String driveCapacity) {
        this.driveCapacity.set(driveCapacity);
    }

    public String getDriveType() {
        return driveType.get();
    }

    public SimpleStringProperty driveTypeProperty() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType.set(driveType);
    }

    public String getGpuModel() {
        return gpuModel.get();
    }

    public SimpleStringProperty gpuModelProperty() {
        return gpuModel;
    }

    public void setGpuModel(String gpuModel) {
        this.gpuModel.set(gpuModel);
    }

    public String getOperatingSystem() {
        return operatingSystem.get();
    }

    public SimpleStringProperty operatingSystemProperty() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem.set(operatingSystem);
    }

    public String getOpticalDriveType() {
        return opticalDriveType.get();
    }

    public SimpleStringProperty opticalDriveTypeProperty() {
        return opticalDriveType;
    }

    public void setOpticalDriveType(String opticalDriveType) {
        this.opticalDriveType.set(opticalDriveType);
    }
}
