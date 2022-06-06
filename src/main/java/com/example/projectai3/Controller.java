package com.example.projectai3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {
    HashMap<String, Type> dataHash= new HashMap<>();


    @FXML
    private Button dataFileButton;

    @FXML
    private TextField enterData;

    @FXML
    private Button exitButton;

    @FXML
    private Button openFileButton;

    @FXML
    private Button restButton;

    @FXML
    private TextArea worlds;

    final FileChooser fileChoose = new FileChooser();
    File  file;


    public static void main(String[] args) {
        System.out.println("سبسشب");
    }
    @FXML
    void giveTheData(MouseEvent event) { // Text On Click

    }

    @FXML
    void openFileData(ActionEvent event) {
        fileChoose.setTitle("Please Choose an file");
        file = fileChoose.showOpenDialog(null);


    }


    @FXML
    void showDataFile(ActionEvent event)  {

        String outputFile = "converterData.xlsx";


        try {
            File myObj = new File(String.valueOf(file));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.replaceAll("[0-9]"," ");
                data = data.replaceAll("\\p{Punct}", " ");

                data = data.replaceAll("،", " ");
                data = data.replaceAll(":", " ");
                data = data.replaceAll("؛", " ");
                data = data.replaceAll("\\.", " ");
                data = data.replaceAll("( )+", " ");
                String[] strArray =data.split(" ");
                for (int i=0;i<strArray.length;i++){
                    if (!dataHash.containsKey(strArray[i])){
                        dataHash.put(strArray[i],new Type(1,0));
                    }
                    else
                        dataHash.put(strArray[i], dataHash.get(strArray[i]).setFreq(dataHash.get(strArray[i]).getFreq()+1));
                }

                System.out.println(data);
                worlds.setText(data);


            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

       // worlds.setText(data);


    }

    @FXML
    void resetApp(ActionEvent event) {

    }

    @FXML
    void exitApp(ActionEvent event) {

    }

}
