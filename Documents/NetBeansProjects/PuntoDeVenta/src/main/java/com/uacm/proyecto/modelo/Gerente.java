package com.uacm.proyecto.modelo;

import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Esta clase se encarga de las acciones del gerente
 * @author Carlos Martinez Hernandez
 */
public class Gerente {
    
    private int id;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private String correo;
    private String contraseña;
    private String claveAcceso;

    /**
     * Constructor de la clase
     * @param nombre
     * @param apellidos
     * @param edad
     * @param correo
     * @param contraseña
     * @param claveAcceso 
     */
    public Gerente(String nombre, String apellidos, Integer edad, String correo, String contraseña, String claveAcceso) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correo = correo;
        this.contraseña = contraseña;
        this.claveAcceso = claveAcceso;
    }

    public Gerente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Gerente(int id, String nombre, String apellidos, Integer edad, String correo, String contraseña, String claveAcceso) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correo = correo;
        this.contraseña = contraseña;
        this.claveAcceso = claveAcceso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.apellidos);
        hash = 97 * hash + Objects.hashCode(this.edad);
        hash = 97 * hash + Objects.hashCode(this.correo);
        hash = 97 * hash + Objects.hashCode(this.contraseña);
        hash = 97 * hash + Objects.hashCode(this.claveAcceso);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gerente other = (Gerente) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.correo, other.correo)) {
            return false;
        }
        if (!Objects.equals(this.contraseña, other.contraseña)) {
            return false;
        }
        if (!Objects.equals(this.claveAcceso, other.claveAcceso)) {
            return false;
        }
        return Objects.equals(this.edad, other.edad);
    }

    @Override
    public String toString() {
        return "Gerente{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", edad=" + edad + ", correo=" + correo + ", contrase\u00f1a=" + contraseña + ", claveAcceso=" + claveAcceso + '}';
    }
    
    /**
     * Este metodo se encarga de agregar un producto
     * @param producto 
     */
    public void AgregarProducto(Producto producto) throws SQLException, DAOException{
        DaoManagerImpl dao = new DaoManagerImpl();
        dao.getProductoDAO().agregar(producto);
    }

    /**
     * Este metodo se encarga de validar el acceso de un vendedor
     * @param claveAcceso
     * @return 
     */
    public Boolean ValidarInicioVendedor(String claveAcceso) throws SQLException{
         DaoManagerImpl dao = new DaoManagerImpl();
         Boolean validacion;
         validacion = dao.getGerenteDAO().validaInicioVendedor(claveAcceso);
         
         
        
        return validacion;
    }
    
    /**
     * Este metodo se encarga de editar un producto 
     * @param producto 
     */
    public void EditarProducto(Producto producto){
        
    }
    
    /**
     * Este metodo se encarga de eliminar un producto
     * @param codigo 
     */
    public void EliminarProducto(String codigo){
        
    }
    
    /**
     * Este metodo se encarga de mostrar las ventas
     */
    public void VerVentas(){
        
    }
    
    /**
     * Este metodo se encarga de mostrar los detalles de una venta
     */
    public void VerDetallesVenta(){
        
    }
    
    
}
