package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.controller.alertas.MisAlertas;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Gerente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Esta clase se encarga de el inicio de sesion del vendedor
 * FXML Controller class
 *
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class InicioVendedorController implements Initializable {
    private InicialController controladorInicial;
    private Stage stage;
    @FXML
    private TextField txtNombreVendedor;
    @FXML
    private PasswordField txtCodigoAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Este metodo se encarga de validar el inicio de sesion del vendedor
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void iniciarSesion(ActionEvent event) throws MisExcepciones {
        try{
            assert ValidarDatos() == true:"Hay campos vacios";
            if(ValidarDatos()!=true){
                MisAlertas.infoAlert("Error", "No puedes dejar campos vacios", "Intentalo nuevamente");
            }else{
                Gerente g = new Gerente();
                //MySQLDaoManager dao = new DaoManagerImpl();
                //Boolean validacion;
                //validacion = dao.getGerenteDAO().validaInicioVendedor(txtCodigoAdmin.getText());
                if(g.ValidarInicioVendedor(txtCodigoAdmin.getText()) == true){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_vendedor.fxml"));//Creamos el loader de la vista que queremos ver
                    Parent root = loader.load();
                    VistaVendedorController controlador = loader.getController();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    controlador.init(stage, this, txtNombreVendedor.getText());
                    stage.setTitle("Vista de vendedor");
                    stage.resizableProperty().setValue(false);
                    stage.show();
                    this.stage.close();
                }else{
                    System.out.println("No te han dado acceso");
                    MisAlertas.errorAlert("Error", "NO se te ha dado acceso", "Dile a tu gerente que te de acceso con su clave de acceso");
                }
            }
        }catch(SQLException ex){
            throw new MisExcepciones("Eror en SQL");
        }catch(IOException ex){
            throw new MisExcepciones("Error en la entrada o salida de datos");
        }
    }
    
    private Boolean ValidarDatos(){
        Boolean resultado= false;
        if(txtNombreVendedor.getText().isEmpty() || txtCodigoAdmin.getText().isEmpty()){
            resultado = false;
        }else{
            resultado = true;
        }
        return resultado;
    }
    
    /**
     * Este metodo se encarga de regresar a la vetana anterior y cierra esta ventana
     * @param event
     * @throws IOException 
     */
    @FXML
    private void Regresar(ActionEvent event) throws IOException {
       controladorInicial.show();
       stage.close();
    }

    /**
     * Este metodo recibe el escenario y el controlador anteriores
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
