package com.uacm.proyecto.controller;

import com.uacm.proyecto.controller.MisExcepciones.MisExcepciones;
import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.daoimpl.DaoManagerImpl;
import com.uacm.proyecto.modelo.Venta;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Esta clase se encarga de las acciones de la vita llamada vista_ventas
 * FXML Controller class
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class VistaVentasController implements Initializable {

    private VistaGerenteController controladorAnterior;
    private Stage stage;
    @FXML
    private TableView<VentaTabla> tablaVentas;
    @FXML
    private TableColumn<VentaTabla, Integer> columnaVenta;
    @FXML
    private TableColumn<VentaTabla, String> columnaVendedor;
    @FXML
    private TableColumn<VentaTabla, Double> columnaTotal;
    @FXML
    private TextField txtNombreVendedorBuscar;
    @FXML
    private Label labelGanancias;
    @FXML
    private Label labelProductosVendidos;
    private int posicionTabla;
    public List<Venta> ventas;
    public ObservableList<VentaTabla> ventasLista = FXCollections.observableArrayList();
    @FXML
    private Button btnVerDetallesVentas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            init();
        } catch (MisExcepciones ex) {
            Logger.getLogger(VistaVentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    


    /**
     * Este metodo sirve para volver al escenario anterior y cierra este escenario
     * @param event 
     */
    @FXML
    private void Volver(ActionEvent event) {
        controladorAnterior.show();
        stage.close();
    }

    /**
     * Este metodo recibe y guarda el controlador anterio, el escenario anterior
     * @param stage
     * @param aThis 
     */
    void init(Stage stage, VistaGerenteController aThis) {
        this.controladorAnterior = aThis;
        this.stage = stage;
        btnVerDetallesVentas.setDisable(true);
        btnVerDetallesVentas.setVisible(false);
        llenarTabla();
    }

    /**
     * Este metodo sirve para llevarte a otra vista llamada vista_detalles_ventas
     * @param event
     * @throws MisExcepciones 
     */
    @FXML
    private void DetallesVentas(ActionEvent event) throws MisExcepciones {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_detalles_ventas.fxml"));//Creamos el loader de la vista que queremos ver
            Parent root = loader.load();
            VistaDetallesventasController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            String nombreVendedor = ventasLista.get(posicionTabla).getNombre();
            controlador.init(stage, this,nombreVendedor, Integer.parseInt(txtNombreVendedorBuscar.getText()));
            stage.setTitle("Detalles de la venta");
            stage.resizableProperty().setValue(false);
            stage.show();
            this.stage.close();
        }catch(IOException ex){
            throw new MisExcepciones("Error en la entrada y salida de datos");
        }
    }
    
    /**
     * Este metodo prepara todo para poder mostrar los datos en la tabla de la vista
     * @throws MisExcepciones 
     */
    public void init() throws MisExcepciones{
        try{
            DaoManagerImpl dao = new DaoManagerImpl();
            ventas = new ArrayList<>();
            ventas = dao.getVentaDAO().obtenerTodos();
            columnaVenta.setCellValueFactory(new PropertyValueFactory<>("numeroVenta"));
            columnaVendedor.setCellValueFactory(new PropertyValueFactory("nombre"));
            columnaTotal.setCellValueFactory(new PropertyValueFactory("total"));
            tablaVentas.setItems(ventasLista);
            final ObservableList<VentaTabla> tablaProductosSel = tablaVentas.getSelectionModel().getSelectedItems();
            tablaProductosSel.addListener(selectorTablaVentas);
        }catch(SQLException ex){
            throw new MisExcepciones("Error en SQL");
        }catch(DAOException ex){
            throw new MisExcepciones("Error en el DAO");
        }
    }
    
    /**
     * Este metodo se encarga de llenar los datos de la tabla para poder mostrarlos en la vista
     */
    private void llenarTabla(){
        double precio=0;
        for(int i=0; i<ventas.size(); i++){
            ventasLista.add(new VentaTabla(ventas.get(i).getId(),ventas.get(i).getNumeroVenta(),ventas.get(i).getNombreVendedor(),
                    ventas.get(i).getMonto()));
            precio+=ventas.get(i).getMonto();
        }
        labelGanancias.setText(String.valueOf(String.format("%.2f", precio)));
        labelProductosVendidos.setText(String.valueOf(ventas.size()));
    }

    /**
     * Este metodo sirve para mostrar el escenario
     */
    void show() {
        stage.show();
    }
    
    /**
     * Esto sirve para poder seleccionar una venta de la tabla y que te devuelva su posicion en la tabla
     */
    private final ListChangeListener<VentaTabla> selectorTablaVentas=
            new ListChangeListener<VentaTabla>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends VentaTabla> c) {
            ponerVentaSeleccionado();
        }

        public VentaTabla getTablaVentaSeleccionada(){
            if(tablaVentas != null){
                List<VentaTabla> tabla = tablaVentas.getSelectionModel().getSelectedItems();
                if(tabla.size()==1){
                    final VentaTabla competicionSeleccionada = tabla.get(0);
                    return competicionSeleccionada;
                }
            }
            return null;
        }
        private void ponerVentaSeleccionado() {
            final VentaTabla venta = getTablaVentaSeleccionada();
            posicionTabla = ventasLista.indexOf(venta);
            if(venta!=null){
                txtNombreVendedorBuscar.setText(String.valueOf(ventasLista.get(posicionTabla).getNumeroVenta()));
                System.out.println(posicionTabla);
                btnVerDetallesVentas.setDisable(false);
                btnVerDetallesVentas.setVisible(true);
            }
        }
    };   
}