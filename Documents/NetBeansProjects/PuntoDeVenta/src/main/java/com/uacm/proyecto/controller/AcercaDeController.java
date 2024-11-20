package com.uacm.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Esta clase es el controlador de mi vista AcercaDe.fxml
 *
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class AcercaDeController implements Initializable {

    private InicialController controladorAnterior;
    Stage stage;
    @FXML
    private Label labelTexto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    /**
     * Este metodo es el primero que se inicia en esta clase
     * @param texto
     * @param stage
     * @param aThis 
     */
    void init(String texto, Stage stage, InicialController aThis) {
        this.stage = stage;
        controladorAnterior = aThis;
        labelTexto.setText(texto);
    }

    /**
     * Este metodo cierra la venntana
     * @param event 
     */
    @FXML
    private void Cerrar(ActionEvent event) {
        controladorAnterior.show();
        stage.close();
    }
    
}
