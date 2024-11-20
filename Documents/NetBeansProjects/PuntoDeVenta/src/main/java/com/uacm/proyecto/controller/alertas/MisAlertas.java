package com.uacm.proyecto.controller.alertas;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Esta clase sirve para mostrar alertas dentro de mi aplicacion
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class MisAlertas {
    
    /**
    * @private Metodo que se encarga de invocar una alerta de Informacion	
    * @param title El titulo de la alerta
    * @param header El encabezado de la alerta
    * @param content El contenido de la alerta
    */
    public static void infoAlert(String title, String header, String content){
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(title);
	alert.setHeaderText(header);
	alert.setContentText(content);  
	alert.showAndWait();	
        }
    /**
     * @private Metodo que se encarga de invocar una alerta de Error
     * @param title El titulo de la alerta
     * @param header El encabezado de la alerta
     * @param content El contenido de la alerta
     */
    public static void errorAlert(String title, String header, String content){
        Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle(title);
        alert.setHeaderText(header);
	alert.setContentText(content);
        alert.showAndWait();	
    }  
    /**
     * @private Metodo que se encarga de invocar una alerta de confirmacion para eliminar un producto
    * @param title El titulo de la alerta  
    * @param header El encabezado de la alerta
    * @param content El contenido de la alerta
    */
    public static int confirmAlertEliminar(String title, String header, String content){
        Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(title);
        alert.setHeaderText(header);
	alert.setContentText(content);
        int respuesta=0;
	Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            MisAlertas.infoAlert("ACCION REALIZADA", "Producto eliminado", "Haz eliminado un producto");
            respuesta=1;
	}else{
            respuesta = 0;
	}
        return respuesta;
    }
    /**
    * @private Metodo que se encarga de invocar una alerta de confirmacion para cerrarSesion	
    * @param title El titulo de la alerta
    * @param header El encabezado de la alerta
    * @param content El contenido de la alerta
    */
    public static int confirmAlertCerrarSesion(String title, String header, String content){
        Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        int respuesta=0;
        Optional<ButtonType> result = alert.showAndWait();
	if(result.get() == ButtonType.OK){
            MisAlertas.infoAlert("ACCION REALIZADA", "Haz cerrado tu sesion", "Vuelve pronto");
            respuesta=1;
	}else{
            respuesta = 0;
	}
        return respuesta;
        }
        /**
    * @private Metodo que se encarga de invocar una alerta de confirmacion para eliminar cuenta	
    * @param title El titulo de la alerta
    * @param header El encabezado de la alerta
    * @param content El contenido de la alerta
    */
    public static int confirmAlertEliminarCuenta(String title, String header, String content){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
	alert.setHeaderText(header);
        alert.setContentText(content);
        int respuesta=0;
        Optional<ButtonType> result = alert.showAndWait();
	if(result.get() == ButtonType.OK){
            MisAlertas.infoAlert("ACCION REALIZADA", "Haz eliminado tu cuenta", "Para volver, necesitas registrarte otra vez");
            respuesta=1;
        }else{
            respuesta = 0;
	}
        return respuesta;
    }
}
