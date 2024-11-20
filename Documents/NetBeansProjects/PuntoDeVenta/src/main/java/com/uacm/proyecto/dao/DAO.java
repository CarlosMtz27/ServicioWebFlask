package com.uacm.proyecto.dao;

import com.uacm.proyecto.modelo.Gerente;
import java.util.List;

/**
 * Interfaz que define los metodos que utilizaran las clases que implementen de esta
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public interface DAO<T, K> {
    void agregar(T dato) throws DAOException;
    void eliminar(T dato) throws DAOException;
    void editar(T dato) throws DAOException;
    List<T> obtenerTodos() throws DAOException;
    T obtener(K id) throws DAOException;
}
