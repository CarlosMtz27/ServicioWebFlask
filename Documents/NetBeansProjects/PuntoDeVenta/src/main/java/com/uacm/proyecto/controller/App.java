package com.uacm.proyecto.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Carlos Martinez Hernandez
 * @version 1.0
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    /**
     * Este metodo start se ejecuta cuando inicia la aplicacion
     * @param primaryStage
     * @throws IOException 
     */
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inicial.fxml"));//se carga la vista fxml
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        InicialController controlador = loader.getController();
        controlador.setStage(primaryStage);
        primaryStage.setTitle("Tienda de abarrotes");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
   }

    /**
     * Este es el metodo main
     * @param args 
     */
    public static void main(String[] args) {
        launch();
    }

}