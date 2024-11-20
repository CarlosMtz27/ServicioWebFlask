package com.uacm.proyecto.controller.MisExcepciones;

/**
 * Esta clase es para crear mis excepciones propias
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class MisExcepciones extends Exception{

    public MisExcepciones() {
    }

    public MisExcepciones(String message) {
        super(message);
    }

    public MisExcepciones(String message, Throwable cause) {
        super(message, cause);
    }

    public MisExcepciones(Throwable cause) {
        super(cause);
    }

    public MisExcepciones(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public void CamposVaciosException(){
        
    }
    
}
