module org.example.oopjavagroupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.oopjavagroupproject to javafx.fxml;
    opens org.example.oopjavagroupproject.model to javafx.base;

    exports org.example.oopjavagroupproject;
    exports org.example.oopjavagroupproject.model;
    exports org.example.oopjavagroupproject.data;

    provides java.util.spi.ResourceBundleProvider with org.example.oopjavagroupproject.PropertiesBundleProvider;
}
