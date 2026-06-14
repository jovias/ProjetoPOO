module com.example.atleta {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.atleta to javafx.fxml;
    exports com.example.atleta;
}