module com.example.finalforcompsci2024 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.datatransfer;
    requires java.desktop;

    opens com.example.finalforcompsci2024 to javafx.fxml;
    exports com.example.finalforcompsci2024;
}