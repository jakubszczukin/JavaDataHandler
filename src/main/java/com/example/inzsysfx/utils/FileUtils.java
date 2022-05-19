package com.example.inzsysfx.utils;


import com.example.inzsysfx.entities.LaptopTableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static void saveXmlFile(File file, ObservableList<LaptopTableData> list){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("laptops");
            int lapCount = 1;

            DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime ldt = LocalDateTime.now();

            //ZMIENIC JEZELI NULL/EQUALS("BRAK INFORMACJI")/== 0 MA NIE WPISYWAC ATRYBUTOW
            root.addAttribute("moddate", ldt.truncatedTo(ChronoUnit.SECONDS).format(dtf));

            for(LaptopTableData laptop : list){
                //Element laptopNode = root.addElement("laptop").addAttribute("id", Integer.toString(lapCount));
                Element laptopNode = root.addElement("laptop").addAttribute("id", Integer.toString(laptop.getId()));
                laptopNode.addElement("manufacturer").addText(correctUnknownInfo(laptop.getManufacturer()));

                Element screenNode = laptopNode.addElement("screen").addAttribute("touch", correctUnknownInfo(laptop.getIsTouchscreen()));
                screenNode.addElement("size").addText(correctUnknownInfo(laptop.getDiagonal()));
                screenNode.addElement("resolution").addText(correctUnknownInfo(laptop.getResolution()));
                screenNode.addElement("type").addText(laptop.getMatrixType());

                Element cpuNode = laptopNode.addElement("processor");
                cpuNode.addElement("name").addText(laptop.getCpuModel());
                cpuNode.addElement("physical_cores").addText(Long.toString(laptop.getCpuCores()));
                cpuNode.addElement("clock_speed").addText(Long.toString(laptop.getCpuClockSpeed()));

                laptopNode.addElement("ram").addText(laptop.getRam());

                Element driveNode = laptopNode.addElement("disc").addAttribute("type", correctUnknownInfo(laptop.getDriveType()));
                driveNode.addElement("storage").addText(laptop.getDriveCapacity());

                Element gpuNode = laptopNode.addElement("graphic_card");
                gpuNode.addElement("name").addText(laptop.getGpuModel());
                gpuNode.addElement("memory").addText(laptop.getGpuMemory());

                laptopNode.addElement("os").addText(laptop.getOperatingSystem());
                laptopNode.addElement("disc_reader").addText(laptop.getOpticalDriveType());

                System.out.println(correctUnknownInfo(laptop.getDriveType()));

                lapCount++;
            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            //XMLWriter writer = new XMLWriter(System.out, format);
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ObservableList<LaptopTableData> readXmlFile(File file) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);

            List<Node> nodeList = document.selectNodes("//laptops/laptop");
            ObservableList<LaptopTableData> laptopList = FXCollections.observableArrayList();

            for (Node node : nodeList) {
                int id = Integer.parseInt(node.valueOf("@id"));
                String manufacturer = checkIfStringEmpty(node.selectSingleNode("manufacturer").getText());
                Node screenNode = node.selectSingleNode("screen");
                String isTouchscreen = screenNode.valueOf("@touch");
                String diagonal = checkIfStringEmpty(screenNode.selectSingleNode("size").getText());
                String resolution = checkIfStringEmpty(screenNode.selectSingleNode("resolution").getText());
                String matrixType = checkIfStringEmpty(screenNode.selectSingleNode("type").getText());
                Node cpuNode = node.selectSingleNode("processor");
                String cpuModel = checkIfStringEmpty(cpuNode.selectSingleNode("name").getText());
                Long cpuCores = checkIfLongEmpty(cpuNode.selectSingleNode("physical_cores").getText());
                Long cpuClockSpeed = checkIfLongEmpty(cpuNode.selectSingleNode("clock_speed").getText());
                String ram = checkIfStringEmpty(node.selectSingleNode("ram").getText());
                Node driveNode = node.selectSingleNode("disc");
                String driveType = checkIfStringEmpty(node.valueOf("@type"));
                String driveCapacity = checkIfStringEmpty(driveNode.selectSingleNode("storage").getText());
                Node gpuNode = node.selectSingleNode("graphic_card");
                String gpuModel = checkIfStringEmpty(gpuNode.selectSingleNode("name").getText());
                String gpuMemory = checkIfStringEmpty(gpuNode.selectSingleNode("memory").getText());
                String operatingSystem = checkIfStringEmpty(node.selectSingleNode("os").getText());
                String opticalDriveType = checkIfStringEmpty(node.selectSingleNode("disc_reader").getText());
                laptopList.add(new LaptopTableData(id, manufacturer, diagonal, resolution, matrixType, isTouchscreen, cpuModel, cpuCores, cpuClockSpeed, ram, driveCapacity, driveType, gpuModel, gpuMemory, operatingSystem, opticalDriveType));
            }
            return laptopList;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveTxtFile(File file, ObservableList<LaptopTableData> list){
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(LaptopTableData laptop : list){
                bw.write(laptop.getLaptopDataAsString());
                bw.write("\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<LaptopTableData> readTxtFile(File file){
        ObservableList<LaptopTableData> laptopList = FXCollections.observableArrayList();
        try{
            Scanner scanner = new Scanner(file);
            int id = 1;
            while(scanner.hasNextLine()){
                String singleLine = scanner.nextLine();
                laptopList.add(parseTxtLine(singleLine, id++));
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return laptopList;
    }

    public static LaptopTableData parseTxtLine(String singleLine, int id){
        Scanner scanner = new Scanner(singleLine);
        scanner.useDelimiter(";");
        if(scanner.hasNext()){
            return new LaptopTableData(
                    id,
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfLongEmpty(scanner.next()),
                    checkIfLongEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next()),
                    checkIfStringEmpty(scanner.next())
            );
        }
        return null;
    }

    public static String checkIfStringEmpty(String str){
        return str.equals("") ? "Brak informacji" : str;
    }

    public static Long checkIfLongEmpty(String str){
        return str.equals("") ? 0 : Long.parseLong(str);
    }

    public static String correctUnknownInfo(String str){
        return str.equals("Brak informacji") ? "" : str;
    }

    public static String correctUnknownInfo(Long lng){
        return (lng == 0) ? "" : lng.toString();
    }

}
