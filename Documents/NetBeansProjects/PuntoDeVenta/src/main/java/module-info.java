
module com.uacm.proyecto.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.uacm.proyecto.controller to javafx.fxml;
    exports com.uacm.proyecto.controller;
}
