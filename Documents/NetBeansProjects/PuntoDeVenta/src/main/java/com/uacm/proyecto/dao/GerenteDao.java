package com.uacm.proyecto.dao;

import com.uacm.proyecto.modelo.Gerente;
import java.util.List;

/**
 * Esta interfaz se encarga de los metodos propios del gerente
 *@version 1.0
 * @author Carlos Martinez Hernandez
 */
public interface GerenteDao extends DAO<Gerente, Integer>{
    Boolean validaInicioVendedor(String claveAcceso);
    Gerente validarUsuario(String correo, String pass);

}
