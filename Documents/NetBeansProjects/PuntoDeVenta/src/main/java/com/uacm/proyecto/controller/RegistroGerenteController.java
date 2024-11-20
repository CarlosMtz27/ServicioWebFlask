package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.CamposVaciosException;
import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.controller.alertas.MisAlertas;
import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Gerente;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Esta clase se encarga del registro de un gerente
 * @author Carlos Martinez Hernandez
 */

public class RegistroGerenteController implements Initializable {

    private InicioDuenioController controladorAnterior;
    private Stage stage;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCodigoAcceso;
    @FXML
    private PasswordField txtContrasenia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    /**
     * Este metodo se encarga del registro de un gerent
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void Registrar(ActionEvent event) throws MisExcepciones, CamposVaciosException{
        try{
            if(ValidarDatos()!=true){
                MisAlertas.infoAlert("Error", "No puedes dejar campos vacios", "Intentalo nuevamente");
                throw new CamposVaciosException("No puedes dejar campos vacios");
            }else{
                Gerente gerente = new Gerente(txtNombre.getText(),txtApellido.getText(),ConvertirEdad(), txtCorreo.getText(), txtContrasenia.getText(),
                        txtCodigoAcceso.getText());
                DaoManagerImpl dm = new DaoManagerImpl();
                dm.getGerenteDAO().agregar(gerente);
                controladorAnterior.show();
                stage.close();
            }
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en el DAO");
        }
    }
    
    private Boolean ValidarDatos(){
        Boolean resultado;
        int edad=0;
            if(txtEdad.getText().isEmpty()){
                edad=0;
            }else{
                edad = Integer.parseInt(txtEdad.getText());
            }
        if(txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || edad==0 || txtCorreo.getText().isEmpty()||
                    txtContrasenia.getText().isEmpty()|| txtCodigoAcceso.getText().isEmpty()){
            resultado=false;
            
        }else{
            resultado=true;
        }
        return resultado;
    }
    
    private int ConvertirEdad(){
        int edad=0;
        edad = Integer.parseInt(txtEdad.getText());
        
        return edad;
    }

    /**
     * Este metodo recibe el controlador y el escenario del controlado anterior
     * @param stage
     * @param aThis 
     */
    void init(Stage stage, InicioDuenioController aThis) {
        this.controladorAnterior = aThis;
        this.stage = stage;
        
    }

    /**
     * Este metodo se encarga de regresar a la ventana anterior y cierra esta ventana
     * @param event 
     */
    @FXML
    private void Regresar(ActionEvent event) {
        controladorAnterior.show();
        stage.close();
    }
    
}
