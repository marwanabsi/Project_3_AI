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
                //Tow Words
                for (int i=0;i<strArray.length;i++){
                    if (i<(strArray.length-1)){
                        {
                            if(!dataHash.containsKey(strArray[i] + " " + strArray[i + 1]))
                                dataHash.put((strArray[i]+" "+strArray[i+1]),new Type(1,0));
                            else
                                dataHash.put((strArray[i] + " " + strArray[i + 1]),
                                        dataHash.get(strArray[i] + " " + strArray[i + 1]).setFreq(dataHash.get(strArray[i]
                                                + " " + strArray[i + 1]).getFreq() + 1));
                        }

                    }
                }
                //Three Words
                for (int i=0;i<strArray.length;i++){
                    if (i<(strArray.length-2)){
                        if (!dataHash.containsKey(strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]))
                        dataHash.put((strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]),new Type(1,0));
                        else
                            dataHash.put((strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]),
                                    dataHash.get(strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]).setFreq(dataHash.get(strArray[i]+" "+strArray[i+1]+" "+strArray[i+2]).getFreq()+1));
                    }
                    }
                prob();
                printOnFile();
//                System.out.println(data);
//                worlds.setText(data);


            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

       // worlds.setText(data);


    }

    public void prob(){
        for (String key : dataHash.keySet()){
            String [] arrayOfString =  key.split(" ");
            if (arrayOfString.length==2){
               double pr1= dataHash.get(arrayOfString[0]).getFreq();
                double pr2= dataHash.get(arrayOfString[0]+" "+arrayOfString[1]).getFreq();
                dataHash.put((arrayOfString[0] + " " + arrayOfString[1]),
                        dataHash.get(arrayOfString[0] + " " + arrayOfString[1]).setProb((pr2/pr1)));

            }

            if (arrayOfString.length==3){
                double pr2= dataHash.get(arrayOfString[0]+" "+arrayOfString[1]).getFreq();
                double pr3= dataHash.get(key).getFreq();

                dataHash.put(key,dataHash.get(key).setProb((pr3/pr2)));

            }
        }
    }


    public void printOnFile(){
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
               System.out.println(key+","+tab.getFreq()+","+tab.getProb());
              // pw.println(key+","+tab.getFreq()+","+tab.getProb());
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
