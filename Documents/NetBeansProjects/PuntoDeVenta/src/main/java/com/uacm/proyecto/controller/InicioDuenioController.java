package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.CamposVaciosException;
import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.controller.alertas.MisAlertas;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Gerente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Esta clase se encarga de la vista inicial del dueño o gerente
 * FXML Controller class
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class InicioDuenioController implements Initializable {

    /**
     * Estos son los atributos que se utilizaran en esta clase
     */
    private InicialController controladorInicial;
    private Stage stage;
    @FXML
    private TextField txtUsernameGerente;
    @FXML
    private TextField txtPasswordGerente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    /**
     * Este metodo registra a un gerente nuevo
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void registrarGerente(ActionEvent event) throws MisExcepciones, CamposVaciosException {
        try{
            assert getClass().getResource("registro_gerente.fxml") != null : "El archivo registro_gerente.fxml no fue encontrado";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registro_gerente.fxml"));//Creamos el loader de la vista que queremos ver
            Parent root = loader.load();
            RegistroGerenteController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controlador.init(stage, this);
            stage.setTitle("Registro de gerente");
            stage.resizableProperty().setValue(false);
            stage.show();
            this.stage.close();   
        }catch(IOException ex){
            throw new MisExcepciones("Error en la entrada y salida");
        }
    }

    /**
     * Este metodo se encarga de validar el inicio de sesion del gerente
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void iniciarSesionGerente(ActionEvent event) throws MisExcepciones, CamposVaciosException{
        try {
            if(ValidarDatos() !=true){
                MisAlertas.infoAlert("Error", "No puedes dejar campos vacios", "Intentalo nuevamente");
                System.out.println("Puede que tu correo o contraseña este mal");
                throw new CamposVaciosException("No puedes dejar campos vacios");
            }else{
                Gerente validacion = null;
                DaoManagerImpl dao = new DaoManagerImpl();
                validacion = dao.getGerenteDAO().validarUsuario(txtUsernameGerente.getText(), txtPasswordGerente.getText());
                if(validacion != null){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_gerente.fxml"));//Creamos el loader de la vista que queremos ver
                    Parent root = loader.load();
                    VistaGerenteController controlador = loader.getController();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    controlador.init(stage, this, validacion);
                    stage.setTitle("Vista de gerente");
                    stage.resizableProperty().setValue(false);
                    stage.show();
                    this.stage.close();
                }else{
                    MisAlertas.errorAlert("Error", "Correo o contraseña incorrectos", "Ingresa tus datos nuevamente");
                }
            }
        }catch (SQLException ex) {
            throw new MisExcepciones("No se encontraron esos datos");
        }catch(IOException ex){
            throw new MisExcepciones("Error con la entrada y salida de datos"); 
        }
                
    }
    
    private Boolean ValidarDatos(){
        Boolean resultado = false;
        if(txtUsernameGerente.getText().isEmpty() || txtPasswordGerente.getText().isEmpty()){
            resultado = false;
        }else{
            resultado = true;
        }
        return resultado;
    }
    /**
     * Este metodo se encarga de redirigir una ventana atras al usuario y cierra esta ventana
     * @param event 
     */
    @FXML
    private void regresar(ActionEvent event) {
        controladorInicial.show();
        stage.close();
    }

    /**
     * Este metodo se inicia al principio y recibe como parametros el escenario(ventana) y el controlador anterior 
     * @param stage
     * @param aThis 
     */
    void init(Stage stage, InicialController aThis) {
        this.controladorInicial = aThis;
        this.stage = stage;
    }

    /**
     * Este metodo muestra el escenario
     */
    void show() {
        stage.show();
    }
    
}
