package com.uacm.proyecto.controller.MisExcepciones;

/**
 * Esta clase es para lanzar excepciones cuando los campos de texto estan vacios
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class CamposVaciosException extends Exception{

    public CamposVaciosException(String message) {
        super(message);
    }
    
}
