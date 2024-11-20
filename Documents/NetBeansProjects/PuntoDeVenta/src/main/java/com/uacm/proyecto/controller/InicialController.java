package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * Esta es la clase inicial del proyecto donde se podra elegir si ingresar como gerente o como vendedor
 * @author Carlos Martinez Hernandez
 * @version 1.0
 * 
 * JavaFX App
 */
public class InicialController{

    private Stage stage;
    @FXML
    private Button botonSoyVendedor;
    @FXML
    private Button botonSoyGerente;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        
    }    

    /**
     * Este metodo se inicia cuando el usuario desea entrar a la app como vendedor
     * @param event
     * @throws MisExcepciones: Lanza excepciones propias
     */
    @FXML
    private void btnSoyVendedor(ActionEvent event) throws MisExcepciones{
        try{
            assert getClass().getResource("inicio_vendedor.fxml") != null : "El archivo inicio_vendedor.fxml no fue encontrado";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio_vendedor.fxml"));
            Parent root = loader.load();
            InicioVendedorController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controlador.init(stage, this);
            stage.setTitle("Inicio de sesión Vendedor");
            stage.resizableProperty().setValue(false);
            stage.show();
            this.stage.close();
        }catch(IOException ex){
            throw new MisExcepciones("Error al intentar abrir la pantalla inicio_vendedor");
        }
    }

    /**
     * Este metodo se inicia cuando el usuario desea entrar a la app como gerente o dueño
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void btnSoyGerente(ActionEvent event) throws MisExcepciones {
        try{
            assert getClass().getResource("inicio_gerente.fxml") != null : "El archivo inicio_gerente.fxml no fue encontrado";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio_gerente.fxml"));
            Parent root = loader.load();
            InicioDuenioController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controlador.init(stage, this);
            stage.setTitle("Inicio de sesión Gerente");
            stage.resizableProperty().setValue(false);
            stage.show();
            this.stage.close();
        }catch(IOException ex ){
            throw new MisExcepciones("Error al intentar abrir la pantalla 'inicio_gerente.fxml'");
        }
    }

    /**
     * Este metodo recibe el stage y lo inicializa
     * @param primaryStage 
     */
    void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Este metodo muestra el stage
     */
    void show() {
        stage.show();
    }

    /**
     * Este metodo ejecuta la opcion de un boton y cierra la aplicacion
     * @param event 
     */
    @FXML
    private void Cerrar(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Este metodo se encarga de leer un txt y mostrarlo en una vista nueva
     * @param event
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @FXML
    private void AcercaDe(ActionEvent event) throws FileNotFoundException, IOException {
      FileReader archivo; 
      BufferedReader lector;
      String texto="";
      try{
          archivo = new FileReader("texto.txt");
          assert archivo != null : "El archivo no se ha podido abrir";
          if(archivo.ready()){
              lector = new BufferedReader(archivo);
              String cadena;
              while((cadena = lector.readLine()) != null){
                  texto += "\n"+cadena;
              }
          }
          System.out.println(texto);
          assert getClass().getResource("AcercaDe.fxml") != null : "El archivo AcercaDe.fxml no fue encontrado";
          FXMLLoader loader = new FXMLLoader(getClass().getResource("AcercaDe.fxml"));
            Parent root = loader.load();
            AcercaDeController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controlador.init(texto, stage, this);
            stage.setTitle("Acerca De.");
            stage.resizableProperty().setValue(false);
            stage.show();
            this.stage.close();
      }catch(Exception e){
          System.out.println("Error "+e.getMessage());
      }
    }
}
