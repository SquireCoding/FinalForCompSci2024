package com.example.finalforcompsci2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    File selectedFile = null;
    File saveLocation = null;
    Label selectShower=null;
    Label saveShower = null;
    Label numberConfirm = new Label("Entre el numero que quiera mover su texto, por favor.");
    Button numberButton = new Button("Confirmar su numero");
    Button pizzapizza = new Button("Pizza Pizza");
    int shift = -1;
    boolean inputGood =false;
    boolean outputGood = false;
    boolean shiftGood = false;
    Button doShift = new Button("!Mover!");
    Label outputStatus = null;
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        root.getStylesheets().add("stylesheet.css");

        Button fileSelector = new Button("Elegir su archivo");
        Button saveSelector = new Button("Elegir donde usted quiere guardar el archivo modificado");
        TextField shiftEnter = new TextField();

        fileSelector.setOnAction(e -> {
            inputGood =false;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Archivo");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos De Texto", "*.txt"));
            selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile!=null) {
                if (selectShower!=null) root.getChildren().remove(selectShower);
                selectShower = new Label("Su Archivo Elegido: \""+selectedFile.getName()+"\"");
                selectShower.setTranslateY(35);
                root.getChildren().add(selectShower);
                inputGood =true;
//                root.getChildren().remove(fileSelector);
            } else {
                if (selectShower!=null) root.getChildren().remove(selectShower);
                selectShower = new Label("Su seleccion esta nulo. Repita, por favor.");
                selectShower.setTranslateY(35);
                root.getChildren().add(selectShower);
            }
        });
        saveSelector.setOnAction(e -> {
            outputGood = false;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Elegir Locacion a Guardar");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos De Texto", "*.txt"));
            saveLocation = fileChooser.showSaveDialog(stage);
            if (saveLocation!=null) {
                if (saveShower!=null) root.getChildren().remove(saveShower);
                saveShower = new Label("Su Locacion Elegido: \""+saveLocation.getAbsolutePath()+"\"");
                saveShower.setTranslateY(95);
                outputGood = true;
                root.getChildren().add(saveShower);
//                root.getChildren().remove(saveSelector);
            } else {
                if (saveShower!=null) root.getChildren().remove(saveShower);
                saveShower = new Label("Su seleccion esta nulo. Repita, por favor.");
                saveShower.setTranslateY(95);
                root.getChildren().add(saveShower);
            }
        });
        numberButton.setOnAction(e -> {
            shiftGood = false;
            root.getChildren().remove(numberConfirm);
            try {
                int i = Integer.parseInt(shiftEnter.getText());
                if (i>25) {
                    numberConfirm=new Label("Eso es un numero, pero lo necisita ser menor que vientiseis.");
                }else
                if (i<0) {
                    numberConfirm=new Label("Eso es un numero, pero lo necisita ser mas que uno negativo.");
                } else {
                    numberConfirm = new Label("Su seleccion: "+i);
                    shiftGood = true;
                    shift=i;
                }

            } catch (Exception ee) {
                numberConfirm=new Label("Eso no es un numero.");
            }
            numberConfirm.setTranslateY(135);
            root.getChildren().add(numberConfirm);
        });
        doShift.setOnAction(e -> {
            root.getChildren().remove(outputStatus);
            if (inputGood&&outputGood&&shiftGood) {
                try {
                    saveLocation.createNewFile();
                    Scanner s = new Scanner(selectedFile);
                    String fini = "";
                    while (s.hasNextLine()) {
                        char[] line = s.nextLine().toUpperCase().toCharArray();
                        String temp = "";
                        for (char c: line) {
                            temp+=Character.toString(shift(shift,c));
                        }
                        fini+=temp;
                        fini+="\n";
                    }
                    fini=fini.replace(" ","");
                    fini=fini.replace(".","");
                    fini=fini.replace("?","");
                    fini=fini.replace("!","");
                    fini=fini.replace(",","");
                    fini=fini.replace("'","");
                    fini=fini.replace("\"","");
                    fini=fini.replace("`","");
                    FileWriter fw = new FileWriter(saveLocation);
                    fw.write(fini);
                    fw.flush();
                    fw.close();
                    outputStatus =new Label("!Terminado!");
                } catch (Exception ee) {
                    outputStatus=new Label("Tiene una problema.");
                }

            } else {
                outputStatus=new Label("Tiene una problema.");
            }
            outputStatus.setTranslateY(270);
            root.getChildren().add(outputStatus);
        });
        pizzapizza.setOnAction(e -> {
            getHostServices().showDocument("https://littlecaesars.com/en-us/order/delivery/stores/12273/menu/");
            String myString = "147 147th Street Leavenworth, KANSAS 66048";
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        saveSelector.setTranslateY(70);
        fileSelector.setTranslateY(10);
        shiftEnter.setTranslateY(155);
        numberConfirm.setTranslateY(135);
        root.setTranslateX(10);
        pizzapizza.setTranslateX(580);
        pizzapizza.setTranslateY(470);
        numberButton.setTranslateY(155);
        numberButton.setTranslateX(165);
        doShift.setTranslateY(230);
        root.getChildren().addAll(saveSelector, fileSelector, numberConfirm, shiftEnter, pizzapizza,numberButton, doShift);

        stage.setTitle("Cifra de Caesar");
        stage.getIcons().add(new Image("file:Caeser.jpg"));
        Scene scene = new Scene(root, 700, 500);
        scene.setFill(Color.rgb(255, 103, 29));
        stage.setScene(scene);
        stage.show();
    }
    public char shift(int amount, char org) {
        while (amount>25) amount--;
        while (amount<0) amount++;
        if (Character.isAlphabetic(org)) {
            if (Character.isUpperCase(org)) {
                char fin = (char) (((int)org)+amount);
                if (fin>90) fin= (char) (65+(amount-(90-((int)org))));
                return fin;
            } else {
                char fin = (char) (((int)org)+amount);
                if (fin>122) fin= (char) (97+(amount-(122-((int)org))));
                return fin;
            }
        } else return org;
    }
    public static void main(String[] args) {
        launch();
    }
}