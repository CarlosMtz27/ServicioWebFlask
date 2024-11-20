package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.controller.alertas.MisAlertas;
import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Producto;
import com.uacm.proyecto.modelo.Venta;
import com.uacm.proyecto.modelo.VentaDetalle;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Esta clase se encarga de la vista llamada vista_vendedor
 * FXML Controller class
 * @author Carlos Martinez Herandez
 * @version 1.0
 */
public class VistaVendedorController implements Initializable {

    private InicioVendedorController controladorAnterior;
    private Stage stage;
    @FXML
    private Label labelNombreVendedor;
    @FXML
    private ImageView imgVendedor;
    @FXML
    private TableView<ProductoTabla> tablaVenta;
    @FXML
    private TableColumn<ProductoTabla, String> columnaNombre;
    @FXML
    private TableColumn<ProductoTabla, String> columnaDescripcion;
    @FXML
    private TableColumn<ProductoTabla, String> columnaCodigo;
    @FXML
    private TableColumn<ProductoTabla, Double> columnaPrecio;
    @FXML
    private TextField txtBuscarCodigo;
    @FXML
    private Label labelSubTotal;
    @FXML
    private Label labelDescuento;
    @FXML
    private Label labelTotalPagar;
    private TextField txtDescuento;
    public ObservableList<ProductoTabla> productosLista = FXCollections.observableArrayList();

    private int posicionTabla;
    public List<Producto> productos;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnBorrar;
    @FXML
    private TextField txtPago;
    @FXML
    private TextField txtCambio;
    @FXML
    private AnchorPane anchorPaneCobro;
    @FXML
    private Button añadirPago;
    @FXML
    private Button btnCobrar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }    
    
    /**
     * Este metodo ayuda a buscar un producto y mostrarlo en la tabla para su venta
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void buscarProducto(ActionEvent event) throws MisExcepciones {
        try{
            DaoManagerImpl dao = new DaoManagerImpl();
            if(txtBuscarCodigo.getText().isEmpty()){
                MisAlertas.errorAlert("Error", "No puedes dejar el campo de texto vacio", "Ingresa el codigo del producto");
                throw new MisExcepciones("Error no puedes dejar el campo de texto vacio");
            }else if(dao.getProductoDAO().BuscarPorCodigo(txtBuscarCodigo.getText()) == null){
                MisAlertas.errorAlert("Error", "No existe el producto", "Ingresa un codigo valido");
            }else{
                anchorPaneCobro.setVisible(true);
                btnCobrar.setVisible(true);
                String codigo = txtBuscarCodigo.getText();
                Producto producto=null;
                producto = dao.getProductoDAO().BuscarPorCodigo(codigo);
                if(producto.getCantidad()==0){
                    MisAlertas.errorAlert("ERROR", "Producto no disponible","No hay productos en stock");
                }else{
                    productosLista.add(new ProductoTabla(producto.getId(),producto.getNombre(),producto.getDescripcion(),producto.getCodigo(),
                    producto.getPrecio(), producto.getCantidad()));
                }
                init();
            }
           
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }
    }

    /**
     * Este metodo sirve para realizar el cobro de la venta
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void cobrarVenta(ActionEvent event) throws MisExcepciones {
        try{
            DaoManagerImpl dao = new DaoManagerImpl();
            String nombre = labelNombreVendedor.getText();
            List<Integer> numerosVentas= new ArrayList();
            numerosVentas = dao.getVentaDAO().numeroVenta();
            //System.out.println(numerosVentas != null);
            LocalDate fechaLocal = LocalDate.now();
            Integer numero;
            if(numerosVentas.size() == 0){
                numero = 1;
            }else{
                numero = numerosVentas.get(numerosVentas.size()-1);
            }
            if(txtPago.getText().isEmpty()){
                MisAlertas.errorAlert("ERROR...", "Ingrese la cantidad de pago", "Ingrese una cantidad correcta");
            }else{
                System.out.println(numero);
                Date fecha = java.sql.Date.valueOf(fechaLocal);
                Double monto = Double.parseDouble(labelTotalPagar.getText());
                Venta venta =  new Venta(nombre,numero+1,fecha,monto);
                dao.getVentaDAO().agregar(venta);
                System.out.println(numero);
                GenerarVentaDetalles(numero+1);
                anchorPaneCobro.setVisible(false);
                btnBorrar.setVisible(false);
                btnCobrar.setVisible(false);
                txtPago.setText("");
                txtCambio.setText("");
                MisAlertas.infoAlert("ACCION REALIZADA", "Compra realizada con exito!!!", "Gracias por su compra");
            }
            
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en DAO");
        }
    }

    /**
     * Este metodo sirve para borrar de la tabla de comprar un producto
     * @param event 
     */
    @FXML
    private void borrarVenta(ActionEvent event) {
        Double precio = productosLista.get(posicionTabla).getPrecio();
        productosLista.remove(posicionTabla);
        VerCosto();
        if(productosLista.isEmpty()){
            anchorPaneCobro.setVisible(false);
            btnBorrar.setVisible(false);
            btnCobrar.setVisible(false);
            txtPago.setText("");
            txtCambio.setText("");
        }
        
    }

    /**
     * Este metodo recibe y guarda el controlador anterior, el escenario anterior y recibe el nombre del vendedor
     * @param stage
     * @param aThis
     * @param nombre 
     */
    void init(Stage stage, InicioVendedorController aThis, String nombre) {
        this.controladorAnterior = aThis;
        this.stage = stage;
        labelNombreVendedor.setText(nombre);
        btnBorrar.setVisible(false);
        anchorPaneCobro.setVisible(false);
        btnCobrar.setVisible(false);
    }

    /**
     * Este metodo se encarga de calcular los costos
     */
    private void VerCosto() {
        Double precio=0.0;
        for(int i=0; i<productosLista.size(); i++){
            precio += productosLista.get(i).getPrecio();
        }
        System.out.println(precio);
        labelSubTotal.setText(String.valueOf(String.format("%.2f", precio)));
        labelTotalPagar.setText(String.valueOf(String.format("%.2f", precio)));
        labelDescuento.setText("0");
    }
    /**
     * Este metodo se encarga de preparar todo para poder mostrar los datos en la tabla de la vista
     */
    void init(){
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        columnaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        tablaVenta.setItems(productosLista);
        VerCosto();
        final ObservableList<ProductoTabla> tablaProductosSel = tablaVenta.getSelectionModel().getSelectedItems();
            tablaProductosSel.addListener(selectorTablaProductos);
    }

    /**
     * Este metodo genera los detalles de la venta y los guarda en la base de datos
     * @param numeroVenta
     * @throws MisExcepciones 
     */
    private void GenerarVentaDetalles(int numeroVenta) throws MisExcepciones{
        try{
            DaoManagerImpl dao = new DaoManagerImpl();
            Double monto = Double.parseDouble(labelTotalPagar.getText());
            for(int i=0; i<productosLista.size(); i++){
                Producto p = new Producto(productosLista.get(i).getId(),productosLista.get(i).getNombre(),productosLista.get(i).getDescripcion(),
                        productosLista.get(i).getPrecio(),productosLista.get(i).getCantidad()-1,productosLista.get(i).getCodigo());
                VentaDetalle ventadetalle = new VentaDetalle(numeroVenta,p.getId(),1,p.getPrecio());
                dao.getVentaDetalleDAO().agregar(ventadetalle);
                dao.getProductoDAO().editar(p);
            }
            borrarLista();
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en el DAO");
        }
    }

    /**
     * Este metodo borra los elementos de la lista de los productos
     */
    private void borrarLista() {
        for(int i=productosLista.size()-1; i>=0; i--){
            productosLista.remove(i);
        }
        txtBuscarCodigo.setText("");
        init();
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
            if(tablaVenta != null){
                List<ProductoTabla> tabla = tablaVenta.getSelectionModel().getSelectedItems();
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
                btnBorrar.setVisible(true);
            }
        }
    };

    /**
     * Este metodo sirve para cerrar la sesion del vendedor
     * @param event 
     */
    @FXML
    private void cerrarSesion(ActionEvent event) {
        int respuesta=0;
        respuesta = MisAlertas.confirmAlertCerrarSesion("Cerrar sesion", "Vas a cerrar tu sesion", "Deseas cerrar sesion "+labelNombreVendedor.getText());
        if(respuesta==1){
            controladorAnterior.show();
            stage.close();
        }
    }

    @FXML
    private void AñadirPago(ActionEvent event) {
        if(txtPago.getText().isEmpty()){
            MisAlertas.errorAlert("ERROR!!!", "Tienes que ingresar una cantidad", "Ingresa una cantidad e intentalo de nuevo");
        }else if(Double.parseDouble(txtPago.getText()) < Double.parseDouble(labelTotalPagar.getText())){
            MisAlertas.errorAlert("ERROR!!!", "Cantidad no valida", "Ingresa una cantidad mayor a la venta total");
        }else{
            Double pago = Double.parseDouble(txtPago.getText());
            Double total = Double.parseDouble(labelTotalPagar.getText());
            Double cambio = pago - total;
            txtCambio.setText(String.valueOf(String.format("%.2f", cambio)));
        }
    }

}
