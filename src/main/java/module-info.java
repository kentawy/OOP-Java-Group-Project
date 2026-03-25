module org.example.oopjavagroupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.oopjavagroupproject to javafx.fxml;
    exports org.example.oopjavagroupproject;
    exports org.example.oopjavagroupproject.database;
    opens org.example.oopjavagroupproject.database to javafx.fxml;
}