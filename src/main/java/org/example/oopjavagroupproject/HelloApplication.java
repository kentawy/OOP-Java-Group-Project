package org.example.oopjavagroupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.oopjavagroupproject.database.DatabaseHandler;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The main entry point for the JavaFX application.
 * This class is responsible for initializing the application, loading the primary stage,
 * and handling scene transitions, including language and theme changes.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class HelloApplication extends Application {

    /**
     * The primary stage of the application, stored statically to allow access from other parts of the application.
     */
    private static Stage primaryStage;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if the FXML file for the main view cannot be loaded.
     */
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

        // Set the application icon
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("logo.png"))));

        // Load the scene with the default locale (Ukrainian)
        loadScene(new Locale("uk", "UA"));

        primaryStage.setMinWidth(835);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    /**
     * Loads and displays a new scene based on the specified locale.
     * This method is responsible for loading the FXML file, applying the correct resource bundle for internationalization,
     * and setting the scene on the primary stage.
     *
     * @param locale the locale to use for loading the resource bundle.
     * @throws IOException if the FXML file cannot be loaded.
     */
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

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * It's not needed for running from the command line.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
