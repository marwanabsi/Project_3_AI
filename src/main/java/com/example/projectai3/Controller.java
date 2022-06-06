package com.example.projectai3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
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
                for (int i=0;i<strArray.length;i++){
                    if (!dataHash.containsKey(strArray[i] + " " + strArray[i + 1])&&i<strArray.length){
                        dataHash.put((strArray[i]+" "+strArray[i+1]),new Type(1,0));
                    }
                    else {
                        if (i<strArray.length-1){
                        dataHash.put((strArray[i] + " " + strArray[i + 1]),
                                dataHash.get(strArray[i] + " " + strArray[i + 1]).setFreq(dataHash.get(strArray[i] + " " + strArray[i + 1]).getFreq() + 1));
                    }
                    }
                }
                for (int i=0;i<strArray.length;i++){
                    if (!dataHash.containsKey(strArray[i]+" "+strArray[i+1]+" "+strArray[i+2])&&i<strArray.length){
                        dataHash.put((strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]),new Type(1,0));
                    }
                    else{
                        if (i<strArray.length-2){
                        dataHash.put((strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]),
                                dataHash.get(strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]).setFreq(dataHash.get(strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]).getFreq()+1));
                }}}
                prinOnFile();
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


    public void prinOnFile(){
       try {
           FileWriter fw = new FileWriter("output.csv",true);
           BufferedWriter bw = new BufferedWriter(fw);
           PrintWriter pw = new PrintWriter(bw);

           for (int i=0;i<dataHash.size();i++){

               System.out.println(dataHash.keySet());

           }

           for ( Map.Entry<String, Type> entry : dataHash.entrySet()) {
               String key = entry.getKey();
               Type tab = entry.getValue();
               pw.println(key+","+tab.getFreq()+","+tab.getProb());
           }

           pw.flush();
           pw.close();


       }catch (Exception e){

       }

    }


    @FXML
    void resetApp(ActionEvent event) {

    }

    @FXML
    void exitApp(ActionEvent event) {

    }

}
