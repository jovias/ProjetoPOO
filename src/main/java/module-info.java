module com.example.atleta {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mitra to javafx.fxml;
    exports com.mitra;
}