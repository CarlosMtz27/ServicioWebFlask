package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.controller.alertas.MisAlertas;
import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Gerente;
import com.uacm.proyecto.modelo.Producto;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Esta clase se encarga de las acciones de la vista vista_gerente
 * FXML Controller class
 * @author Carlos Martinez Hernandez
 */
public class VistaGerenteController implements Initializable {

    private InicioDuenioController controladorInicial;
    private Stage stage;
    @FXML
    private Label labelNombre;
    @FXML
    private TableView<ProductoTabla> tablaProductos;
    @FXML
    private TableColumn<ProductoTabla, String> columnaNombreProducto;
    @FXML
    private TableColumn<ProductoTabla, String> columnaDescripcionProducto;
    @FXML
    private TableColumn<ProductoTabla, Double> columnaPrecioProducto;
    @FXML
    private TableColumn<ProductoTabla, String> columnaCodigoProducto;
    @FXML
    private TableColumn<ProductoTabla, Integer> columnaCantidadProducto;
    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtCodigoProducto;
    @FXML
    private TextField txtCantidadProducto;
    @FXML
    private TextField txtPrecioProducto;
    @FXML
    private TextArea txtDescripcionProducto;
    @FXML
    private TextField txtbusquedaCodigo;
    
    private int posicionTabla;
    public List<Producto> productos;

    public ObservableList<ProductoTabla> productosLista = FXCollections.observableArrayList();
    @FXML
    private TextField txtId;
    @FXML
    private TableColumn<ProductoTabla, Integer> columnaIdProducto;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminarCuenta;
    
    private Gerente gerente;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desactivarBotones();
        init();
    }    

    /**
     * Este metodo sirve para guardar un producto en la base de datos
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void agregarProducto(ActionEvent event) throws MisExcepciones, DAOException{
        try{
            if(ValidarDatos()!=true){
                MisAlertas.errorAlert("Error!!!", "No puedes dejar campos vacio", "Llena los campos y vuelve a intentarlo");
                
            }else{
                Integer cantidad = Integer.parseInt(txtCantidadProducto.getText());
                Double precio = Double.parseDouble(txtPrecioProducto.getText());
                DaoManagerImpl dao = new DaoManagerImpl();
                Gerente g = new Gerente();
                Producto producto = new Producto(txtNombreProducto.getText(),txtDescripcionProducto.getText(), precio,
                        cantidad,txtCodigoProducto.getText());
                g.AgregarProducto(producto);
                //dao.getProductoDAO().agregar(producto = new Producto(txtNombreProducto.getText(),txtDescripcionProducto.getText(), precio,
                        //cantidad,txtCodigoProducto.getText()));
                int id = dao.getProductoDAO().ObtenerId(producto);
                productosLista.add(new ProductoTabla(id, txtNombreProducto.getText(),txtDescripcionProducto.getText(),txtCodigoProducto.getText(),
                        precio, cantidad));
                limpiarCamposTexto(); 
            }
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }
    }
    
    private Boolean ValidarDatos(){
        Boolean resultado;
        if(txtCantidadProducto.getText().isEmpty() || txtPrecioProducto.getText().isEmpty() || txtNombreProducto.getText().isEmpty()
                    || txtDescripcionProducto.getText().isEmpty() || txtCodigoProducto.getText().isEmpty()){
            resultado=false;
        }else{
            resultado = true;
        }
        return resultado;
    }

    /**
     * Este metodo sirve para eliminar un producto
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void eliminarProducto(ActionEvent event) throws MisExcepciones {
        try{
            int respuesta=0;
            respuesta = MisAlertas.confirmAlertEliminar("Eliminar producto", "Vas a eliminar un producto", "Deseas hacerlo?");
            if(respuesta==1){
                Producto producto = new Producto();
                DaoManagerImpl dao = new DaoManagerImpl();
                producto = llenarProducto();
                System.out.println(producto);
                producto.setCantidad(0);
                dao.getProductoDAO().editar(producto);
                productosLista.remove(posicionTabla);
                limpiarCamposTexto();   
            }
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en el DAO");
        }
    }

    /**
     * Este metodo sirve para editar un producto
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void editarProducto(ActionEvent event) throws MisExcepciones {
        try{
            Producto producto = new Producto();
            DaoManagerImpl dao = new DaoManagerImpl();
            producto = llenarProducto();
            dao.getProductoDAO().editar(producto);
            ProductoTabla p = new ProductoTabla(producto.getId(), producto.getNombre(),producto.getDescripcion(),producto.getCodigo(),
                    producto.getPrecio(),producto.getCantidad());
            productosLista.set(posicionTabla, p);
            limpiarCamposTexto();
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en el DAO");
        }
    }
   
    /**
     * Esto sirve para poder seleccionar un producto de la tabla y que te devuelva su posicion en la tabla
     */
    private final ListChangeListener<ProductoTabla> selectorTablaProductos=
            new ListChangeListener<ProductoTabla>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends ProductoTabla> c) {
            ponerProductoSeleccionado();
        }

        public ProductoTabla getTablaProductoSeleccionada(){
            if(tablaProductos != null){
                List<ProductoTabla> tabla = tablaProductos.getSelectionModel().getSelectedItems();
                if(tabla.size()==1){
                    final ProductoTabla competicionSeleccionada = tabla.get(0);
                    return competicionSeleccionada;
                }
            }
            return null;
        }
        private void ponerProductoSeleccionado() {
            final ProductoTabla producto = getTablaProductoSeleccionada();
            posicionTabla = productosLista.indexOf(producto);
            if(producto!=null){
                txtId.setText(String.valueOf(producto.getId()));
                txtNombreProducto.setText(producto.getNombre());
                txtDescripcionProducto.setText(producto.getDescripcion());
                txtCodigoProducto.setText(producto.getCodigo());
                txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
                txtCantidadProducto.setText(String.valueOf(producto.getCantidad()));
                activarBotones();
            }
        }
    };


    /**
     * Este metodo sirve para ir a la vista llamada vista_ventas
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void mostrarVentas(ActionEvent event) throws MisExcepciones {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_ventas.fxml"));//Creamos el loader de la vista que queremos ver
            Parent root = loader.load();
            VistaVentasController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controlador.init(stage, this);
            stage.setTitle("Ventas realizadas");
            stage.show();
            this.stage.close();
        }catch(IOException ex){
            throw new MisExcepciones("Error con la entrada y salida de datos");
        }
    }
    /**
     * Este metodo recibe y guarda el controlador anterior, el escenario anterio y el gerente con el que se ingreso
     * @param stage
     * @param aThis
     * @param gerente 
     */
    void init(Stage stage, InicioDuenioController aThis, Gerente gerente) {
        this.controladorInicial = aThis;
        this.stage = stage;
        labelNombre.setText(gerente.getNombre());
        this.gerente = gerente;
        llenarTabla();
    }

    /**
     * Este metodo prepara la tabla para poder mostrarla en la vista
     */
    void init(){
        try {
            DaoManagerImpl dao = new DaoManagerImpl();
            productos = new ArrayList<>();
            productos = dao.getProductoDAO().obtenerTodos();
            tablaProductos.refresh();
            columnaIdProducto.setCellValueFactory(new PropertyValueFactory("id"));
            columnaNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnaDescripcionProducto.setCellValueFactory(new PropertyValueFactory("descripcion"));
            columnaPrecioProducto.setCellValueFactory(new PropertyValueFactory("precio"));
            columnaCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad"));
            columnaCodigoProducto.setCellValueFactory(new PropertyValueFactory("codigo"));
            tablaProductos.setItems(productosLista);
            
            FilteredList<ProductoTabla> filtroDatos = new FilteredList<>(productosLista, b->true);
            
            txtbusquedaCodigo.textProperty().addListener((observable, oldValue, newValue) -> {
                filtroDatos.setPredicate(producto -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if(producto.getCodigo().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                        
                    }else if(producto.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                    }else if(producto.getDescripcion().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                    }else{
                        return false;
                    }
                });
            });
            SortedList<ProductoTabla> sortedDatos = new SortedList<>(filtroDatos);
            
            sortedDatos.comparatorProperty().bind(tablaProductos.comparatorProperty());
            
            tablaProductos.setItems(sortedDatos);
            
            final ObservableList<ProductoTabla> tablaProductosSel = tablaProductos.getSelectionModel().getSelectedItems();
            tablaProductosSel.addListener(selectorTablaProductos);
        } catch (SQLException ex) {
            Logger.getLogger(VistaGerenteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(VistaGerenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Este metodo sirve para cerrar la sesion del gerente
     * @param event 
     */
    @FXML
    private void cerrarSesion(ActionEvent event) {
        int respuesta=0;
        respuesta = MisAlertas.confirmAlertCerrarSesion("Cerrar sesion", "Vas a cerrar tu sesion", "Deseas cerrar sesion "+labelNombre.getText());
        if(respuesta==1){
            controladorInicial.show();
            stage.close();
        }
    }
    
    /**
     * Este metodo sirve para llenar la tabla que se mostrara en la vista
     */
    private void llenarTabla(){
        for(int i=0; i<productos.size(); i++){
            productosLista.add(new ProductoTabla(productos.get(i).getId(),productos.get(i).getNombre(), productos.get(i).getDescripcion(),productos.get(i).getCodigo(), productos.get(i).getPrecio(), productos.get(i).getCantidad()));
        }
    }
    /**
     * Este metodo sirve para limpiar los campos de texto al iniciar y al querer ingresar un nuevo producto
     */
    private void limpiarCamposTexto(){
        txtNombreProducto.setText("");
        txtDescripcionProducto.setText("");
        txtCodigoProducto.setText("");
        txtPrecioProducto.setText("");
        txtCantidadProducto.setText("");
    }
    
    /**
     * Este metodo es auxiliar para ayudar a llenar el producto y poder ingresarlo a la base de datos
     * @return 
     */
    private Producto llenarProducto(){
        Producto producto = new Producto();
        Double precio = Double.parseDouble(txtPrecioProducto.getText());
        Integer cantidad = Integer.parseInt(txtCantidadProducto.getText());
        Integer id = Integer.parseInt(txtId.getText());
        producto.setId(id);
        producto.setNombre(txtNombreProducto.getText());
        producto.setDescripcion(txtDescripcionProducto.getText());
        producto.setCodigo(txtCodigoProducto.getText());
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        return producto;
    }

    /**
     * Este metodo vigila las acciones del boton nuevo
     * @param event 
     */
    @FXML
    private void Nuevo(ActionEvent event) {
        limpiarCamposTexto();
        desactivarBotones();
    }
    
    /**
     * Este metodo desactiva los botones al seleccionar la opcion nuevo
     */
    private void desactivarBotones(){
        btnAgregar.setVisible(true);
        btnAgregar.setDisable(false);
        btnEditar.setDisable(true);
        btnEditar.setVisible(false);
        btnEliminar.setDisable(true);
        btnEliminar.setVisible(false);
        btnNuevo.setVisible(false);
        btnNuevo.setDisable(true);
    }
    
    /**
     * Este metodo activa los botones al seleccionar un producto de la lista
     */
    private void activarBotones(){
        btnEditar.setDisable(false);
        btnEditar.setVisible(true);
        btnEliminar.setDisable(false);
        btnEliminar.setVisible(true);
        btnNuevo.setVisible(true);
        btnNuevo.setDisable(false);
        btnAgregar.setDisable(true);
        btnAgregar.setVisible(false); 
    }

    /**
     * Este metodo mustra el escenario
     */
    void show() {
        stage.show();
    }

    /**
     * Este metodo sirve para eliminar la cuenta de un gerente
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void EliminarCuenta(ActionEvent event) throws MisExcepciones {
        try{
            int respuesta=0;
            respuesta = MisAlertas.confirmAlertEliminarCuenta("ELIMINAR CUENTA", "Vas a eliminar tu cuenta", "Deseas hacerlo "+labelNombre.getText()+"?");
            if(respuesta==1){
                DaoManagerImpl dao = new DaoManagerImpl();
                dao.getGerenteDAO().eliminar(this.gerente);
                controladorInicial.show();
                stage.close();
            }
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en DAO");
        }
    }
}
