package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Producto;
import com.uacm.proyecto.modelo.Venta;
import com.uacm.proyecto.modelo.VentaDetalle;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Esta clase se encarga de la vista de vista_detalles_ventas
 * FXML Controller class
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class VistaDetallesventasController implements Initializable {

    
    private VistaVentasController controladorAnterior;
    private Stage stage;
    public ObservableList<ProductoTabla> productosLista = FXCollections.observableArrayList();
    @FXML
    private TableView<ProductoTabla> tablaProductos;
    @FXML
    private TableColumn<ProductoTabla, String> columnaNombre;
    @FXML
    private TableColumn<ProductoTabla, String> columnaDescripcion;
    @FXML
    private TableColumn<ProductoTabla, Double> columnaPrecio;
    @FXML
    private TextField txtTotalVenta;
    @FXML
    private TextField txtCantidadProductos;
    @FXML
    private Label labelVendidoPor;
    @FXML
    private Label labelVentaNumero;
    List<Producto> productos;
    List<VentaDetalle> ventas;
    int idVenta;
    List<Integer> productosVentas;
    @FXML
    private TextField txtFecha;

    /**
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    /**
     * Este metodo se encarga de regresar al escenario anterior y cierra este
     * @param event 
     */
    @FXML
    private void Volver(ActionEvent event) {
        controladorAnterior.show();
        stage.close();
    }

    /**
     * Este metodo se inicia al principio
     * @param stage
     * @param aThis
     * @param vendedor
     * @param idVenta
     * @throws MisExcepciones 
     */
    void init(Stage stage, VistaVentasController aThis, String vendedor, int idVenta) throws MisExcepciones {
        this.controladorAnterior = aThis;
        this.stage=stage;
        this.idVenta = idVenta;
        labelVendidoPor.setText(vendedor);
        labelVentaNumero.setText(String.valueOf(idVenta));
        init();
        llenarTabla();
    }

    /**
     * Este metodo se encarga de llenar la tabla para mostrarla en la vista
     * @throws MisExcepciones 
     */
    void llenarTabla() throws MisExcepciones{
        try{
            DaoManagerImpl dao = new DaoManagerImpl();
            Producto p = new Producto();
            Venta venta = new Venta();
            venta = dao.getVentaDAO().obtener(idVenta);
            double precio=venta.getMonto();
            for(int i=0; i<productosVentas.size(); i++){
                p = dao.getProductoDAO().obtener(productosVentas.get(i));
                productosLista.add(new ProductoTabla(p.getId(),p.getNombre(),p.getDescripcion(),p.getCodigo(),p.getPrecio(), p.getCantidad()));
            }
            txtTotalVenta.setText(String.valueOf(String.format("%.2f", precio)));
            txtCantidadProductos.setText(String.valueOf(productosVentas.size()));
            txtFecha.setText(String.valueOf(venta.getFechaVenta()));
        }catch(SQLException ex){
            throw new MisExcepciones("ERROR en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en el DAO");
        }
    }
    
    /**
     * Este metodo tambien se encarga de preparar los datos para mostrarlos en la tabla de la vista
     * @throws MisExcepciones 
     */
    void init() throws MisExcepciones{
        try{
            DaoManagerImpl dao = new DaoManagerImpl();
            productosVentas = dao.getVentaDetalleDAO().obtenerProductos(idVenta);
            assert productosVentas != null: "La lista esta vacia";
            tablaProductos.refresh();
            columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnaDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
            columnaPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
            tablaProductos.setItems(productosLista);
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }
    }
}
