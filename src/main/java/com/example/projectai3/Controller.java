package com.example.projectai3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller {

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

    @FXML
    void giveTheData(MouseEvent event) { // Text On Click

    }

    @FXML
    void openFileData(ActionEvent event) {
        fileChoose.setTitle("Please Choose an file");
        file = fileChoose.showOpenDialog(null);


    }


    @FXML
    void showDataFile(ActionEvent event) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

        //InputStreamReader s= new InputStreamReader(new FileInputStream(file), "UTF-8");

        String line;
        while((line = in.readLine()) != null){
            worlds.setText(line);
            System.out.println(new String(line.getBytes(), "UTF-8"));
            System.out.println(new String("تعطي يونيكود رقما فريدا لكل حرف".getBytes(), "UTF-8"));


        }
        in.close();


    }

    @FXML
    void resetApp(ActionEvent event) {

    }

    @FXML
    void exitApp(ActionEvent event) {

    }

}
