package org.example.oopjavagroupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.oopjavagroupproject.database.DatabaseHandler;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application
{

private static Stage primaryStage;

@Override
public void start(Stage stage) throws IOException {
    primaryStage = stage;
    try {
        DatabaseHandler dbHandler = new DatabaseHandler();
        if (dbHandler.getDbConnection() != null) {
            System.out.println("✅ SUCCESS: Connection to PostgreSQL established!");
        }
    } catch (Exception e) {
        System.err.println("❌ DATABASE ERROR: " + e.getMessage());
    }

    // Load the scene with the default locale (Ukrainian)
    loadScene(new Locale("uk", "UA"));

    primaryStage.setMinWidth(835);
    primaryStage.setMinHeight(600);
    primaryStage.show();
}

public static void loadScene(Locale locale) throws IOException {
    // The system will automatically use our PropertiesBundleProvider due to the 'provides' clause in module-info
    ResourceBundle bundle = ResourceBundle.getBundle("org.example.oopjavagroupproject.messages", locale);
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"), bundle);

    Scene scene = new Scene(fxmlLoader.load(), primaryStage.getScene() != null ? primaryStage.getScene().getWidth() : 835, primaryStage.getScene() != null ? primaryStage.getScene().getHeight() : 600);

    // Apply the default dark theme
    String css = HelloApplication.class.getResource("styles.css").toExternalForm();
    scene.getStylesheets().add(css);

    primaryStage.setTitle(bundle.getString("app.title"));
    primaryStage.setScene(scene);
}

public static void main(String[] args) {
    launch(args);
}
}