package com.example.projectai3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

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

    @FXML
    void giveTheData(MouseEvent event) { // Text On Click

    }

    @FXML
    void openFileData(ActionEvent event) {

        fileChoose.setTitle("Please Choose an file");


    }


    @FXML
    void showDataFile(ActionEvent event) {

    }

    @FXML
    void resetApp(ActionEvent event) {

    }

    @FXML
    void exitApp(ActionEvent event) {

    }

}
