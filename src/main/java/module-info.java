/**
 * Defines the module for the Hostel Management System application.
 * This module specifies its dependencies on JavaFX and SQL modules,
 * opens packages for reflection by JavaFX, exports packages for external use,
 * and provides a custom implementation for the ResourceBundleProvider service.
 */
module org.example.oopjavagroupproject {
    // Specifies the required JavaFX and SQL modules.
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Opens the main package to allow JavaFX FXML to access controllers and resources.
    opens org.example.oopjavagroupproject to javafx.fxml;
    // Opens the model package to allow JavaFX to use property factories with model classes.
    opens org.example.oopjavagroupproject.model to javafx.base;

    // Exports packages to make them accessible to other modules or the classpath.
    exports org.example.oopjavagroupproject;
    exports org.example.oopjavagroupproject.model;
    exports org.example.oopjavagroupproject.data;

    // Provides a custom implementation of ResourceBundleProvider to handle UTF-8 encoded properties files.
    provides java.util.spi.ResourceBundleProvider with org.example.oopjavagroupproject.PropertiesBundleProvider;
}
