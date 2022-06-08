package com.example.projectai3;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Controller {
    HashMap<String, Type> dataHash= new HashMap<>();
    HashMap<String, Type> dataHash2= new HashMap<>();
    ArrayList<Type> list = new ArrayList<Type>();
    LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();

    double finalProl=0;
    double p1 =0 ;
    double p2 =0 ;
    double p3 =0 ;
    double p4 =0 ;
    double p5 =0 ;
    double p6 =0 ;
    @FXML
    private Button dataFileButton;

    @FXML
    private TextArea userData;

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
    void showDataFile(ActionEvent event)  {


        String data="";
        try {
           // File myObj = new File(file);
            int count=0;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNext()) {
                System.out.println("************"+count++);
                data += myReader.nextLine();
                data = data.replaceAll("[0-9]"," ");
                data = data.replaceAll("\\p{Punct}", " ");
                data = data.replaceAll("،", " ");
                data = data.replaceAll(":", " ");
                data = data.replaceAll("؛", " ");
                data = data.replaceAll("\\.", " ");
                data = data.replaceAll("( )+", " ");


//                System.out.println(data);
//                worlds.setText(data);


            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("%%%%%%%%%%%%% Done");


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


           for ( Map.Entry<String, Type> entry : dataHash.entrySet()) {
               String key = entry.getKey();
               Type tab = entry.getValue();
              // System.out.println(key+","+tab.getFreq()+","+tab.getProb());
               pw.println(key+","+tab.getFreq()+","+tab.getProb());
           }

           pw.flush();
           pw.close();


       }catch (Exception e){

       }
        SelectWord();
    }

    int index = 0;
    String[] strArray;
    String targetWord;
    public void SelectWord(){
        final String[] selectedString = new String[1];
        userData.setOnContextMenuRequested(new EventHandler<Event>(){
            @Override
            public void handle(Event arg0){
                selectedString[0] = userData.getSelectedText();
                HashMap<String,Integer> UserInput = new HashMap<>();
                strArray =userData.getText().split(" ");

                for (int i =0;i<strArray.length;i++){
                    if (strArray[i].equals(selectedString[0])){
                        index=i;
                        break;
                    }
                }

                for (int i=0;i<strArray.length;i++){

                    UserInput.put(strArray[i].toString(),i);

                }
                int locationWord = UserInput.get(selectedString[0].trim());

                targetWord = strArray[locationWord-1];
                System.out.println("Target Word is = "+ targetWord);

            }


        });

    }


    @FXML
    void resetApp(ActionEvent event) {
        String selectedWord = " "+targetWord+" ";
        String[] arraylistNew=new String[index];
        if (index >= 0) System.arraycopy(strArray, 0, arraylistNew, 0, index);
        for (String key : dataHash.keySet()){
            if (key.contains(selectedWord)){
                String[] arrayString=key.split(" ");
                if(arrayString.length==3){
                    if (arrayString[0].equals(targetWord)) {
                        strArray[index] = arrayString[1];
                    }
                    if (arrayString[1].equals(targetWord)) {
                        strArray[index] = arrayString[2];
                    }

                    if (arraylistNew.length>=2){
                        String key2=strArray[index-1]+" "+strArray[index];
                        if (dataHash.get(key2)!=null){
                            double prob;
                            if (dataHash.get(key2).getProb()!=0){
                                prob= dataHash.get(key2).getProb();
                                p1=prob;
                            }

                        }


                    }
                    else
                        p1=1;

                    if (arraylistNew.length>=3){
                        String key3=strArray[index-2]+" "+strArray[index-1]+" "+strArray[index];
                        if (dataHash.get(key3)!=null){
                            double prob;
                            if (dataHash.get(key3).getProb()!=0){
                                prob= dataHash.get(key3).getProb();
                                p2=prob;
                            }


                        }

                    }
                    else
                        p2=1;
                    if (arraylistNew.length>=4){
                        String key4=strArray[index-2]+" "+strArray[index-1];
                        if (dataHash.get(key4)!=null){
                            double prob;
                            if (dataHash.get(key4).getProb()!=0){
                                prob= dataHash.get(key4).getProb();
                                p3=prob;
                            }


                        }

                    }
                    else
                        p3=1;
                    if (arraylistNew.length>=5){
                        String key5=strArray[index-3]+" "+strArray[index-2]+" "+strArray[index-1];
                        if (dataHash.get(key5)!=null){
                            double prob;
                            if (dataHash.get(key5).getProb()!=0){
                                prob= dataHash.get(key5).getProb();
                                p4=prob;
                            }


                        }

                    }
                    else
                        p4=1;

                    if (arraylistNew.length>=6){
                        String key6=strArray[index-3]+" "+strArray[index-2];
                        if (dataHash.get(key6)!=null){
                            double prob;
                            if (dataHash.get(key6).getProb()!=0){
                                prob= dataHash.get(key6).getProb();
                                p5=prob;
                            }


                        }

                    }
                    else
                        p5=1;

                    if (arraylistNew.length>=7){
                        String key7=strArray[index-4]+" "+strArray[index-3]+" "+strArray[index-2];
                        if (dataHash.get(key7)!=null){
                            double prob;
                            if (dataHash.get(key7).getProb()!=0){
                                prob= dataHash.get(key7).getProb();
                                p6=prob;
                            }

                        }
                    }
                    else
                        p6=1;


                    finalProl=p1*p2*p3*p4*p5*p6;
                    dataHash2.put(strArray[index],new Type(0,finalProl));

                }

                // For Tow Word
                if(arrayString.length==2){
                    if (arrayString[0].equals(targetWord)){
                        strArray[index]=arrayString[1];

                        if (arraylistNew.length>=2){
                            String key2=strArray[index-1]+" "+strArray[index];
                            if (dataHash.get(key2)!=null){
                                double prob;
                                if (dataHash.get(key2).getProb()!=0){
                                    prob= dataHash.get(key2).getProb();
                                    p1=prob;
                                }


                            }

                        }
                        else
                            p1=1;

                        if (arraylistNew.length>=3){
                            String key3=strArray[index-2]+" "+strArray[index-1]+" "+strArray[index];
                            if (dataHash.get(key3)!=null){
                                double prob;
                                if (dataHash.get(key3).getProb()!=0){
                                    prob= dataHash.get(key3).getProb();
                                    p2=prob;
                                }


                            }

                        }
                        else
                            p2=1;


                        if (arraylistNew.length>=4){
                            String key4=strArray[index-2]+" "+strArray[index-1];
                            if (dataHash.get(key4)!=null){
                                double prob;
                                if (dataHash.get(key4).getProb()!=0){
                                    prob= dataHash.get(key4).getProb();
                                    p3=prob;
                                }


                            }

                        }
                        else
                            p3=1;

                        if (arraylistNew.length>=5){
                            String key5=strArray[index-3]+" "+strArray[index-2]+" "+strArray[index-1];
                            if (dataHash.get(key5)!=null){
                                double prob;
                                if (dataHash.get(key5).getProb()!=0){
                                    prob= dataHash.get(key5).getProb();
                                    p4=prob;
                                }


                            }

                        }
                        else
                            p4=1;

                        if (arraylistNew.length>=6){
                            String key6=strArray[index-3]+" "+strArray[index-2];
                            if (dataHash.get(key6)!=null){
                                double prob;
                                if (dataHash.get(key6).getProb()!=0){
                                    prob= dataHash.get(key6).getProb();
                                    p5=prob;
                                }


                            }

                        }
                        else
                            p5=1;


                        if (arraylistNew.length>=7){
                            String key7=strArray[index-4]+" "+strArray[index-3]+" "+strArray[index-2];
                            if (dataHash.get(key7)!=null){
                                double prob;
                                if (dataHash.get(key7).getProb()!=0){
                                    prob= dataHash.get(key7).getProb();
                                    p6=prob;
                                }

                            }

                        }
                        else
                            p6=1;

                    }
                    finalProl=p1*p2*p3*p4*p5*p6;
                    dataHash2.put(arrayString[1],new Type(0,finalProl));
                }
                if(arrayString.length==2){
                    if (arrayString[0].equals(targetWord)) {
                        strArray[index] = arrayString[1];
                    }

                    if (arraylistNew.length>=2){
                        String key2=strArray[index-1]+" "+strArray[index];
                        if (dataHash.get(key2)!=null){
                            double prob;
                            if (dataHash.get(key2).getProb()!=0){
                                prob= dataHash.get(key2).getProb();
                                p1=prob;
                            }


                        }

                    }
                    else
                        p1=1;

                    if (arraylistNew.length>=3){
                        String key3=strArray[index-2]+" "+strArray[index-1]+" "+strArray[index];
                        if (dataHash.get(key3)!=null){
                            double prob;
                            if (dataHash.get(key3).getProb()!=0){
                                prob= dataHash.get(key3).getProb();
                                p2=prob;
                            }


                        }

                    }
                    else
                        p2=1;

                    if (arraylistNew.length>=4){
                        String key4=strArray[index-2]+" "+strArray[index-1];
                        if (dataHash.get(key4)!=null){
                            double prob;
                            if (dataHash.get(key4).getProb()!=0){
                                prob= dataHash.get(key4).getProb();
                                p3=prob;
                            }


                        }

                    }
                    else
                        p3=1;

                    if (arraylistNew.length>=5){
                        String key5=strArray[index-3]+" "+strArray[index-2]+" "+strArray[index-1];
                        if (dataHash.get(key5)!=null){
                            double prob;
                            if (dataHash.get(key5).getProb()!=0){
                                prob= dataHash.get(key5).getProb();
                                p4=prob;
                            }


                        }

                    }
                    else
                        p4=1;

                    if (arraylistNew.length>=6){
                        String key6=strArray[index-3]+" "+strArray[index-2];
                        if (dataHash.get(key6)!=null){
                            double prob;
                            if (dataHash.get(key6).getProb()!=0){
                                prob= dataHash.get(key6).getProb();
                                p5=prob;
                            }


                        }

                    }
                    else
                        p5=1;

                    if (arraylistNew.length>=7){
                        String key7=strArray[index-4]+" "+strArray[index-3]+" "+strArray[index-2];
                        if (dataHash.get(key7)!=null){
                            double prob;
                            if (dataHash.get(key7).getProb()!=0){
                                prob= dataHash.get(key7).getProb();
                                p6=prob;
                            }

                        }

                    }
                    else
                        p6=1;


                    finalProl=p1*p2*p3*p4*p5*p6;
                    dataHash2.put(arrayString[1],new Type(0,finalProl));
                }


            }
        }
        worlds.setText(dataHash2.toString());
        System.out.println(dataHash2);

        for (Map.Entry<String, Type> entry : dataHash2.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return 0;
            }

            public int compare(String str, String str1) {
                return (str).compareTo(str1);
            }
        });
        for (Type str : list) {
            for (Map.Entry<String, Type> entry : dataHash2.entrySet()) {
                if (entry.getValue().equals(str)) {
                    sortedMap.put(entry.getKey(), String.valueOf(str));
                }
            }
        }
        worlds.setText(dataHash2.toString());
    }


    @FXML
    void exitApp(ActionEvent event) {

    }

}
