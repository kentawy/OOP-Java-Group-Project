module org.example.oopjavagroupproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.oopjavagroupproject to javafx.fxml;
    exports org.example.oopjavagroupproject;
}