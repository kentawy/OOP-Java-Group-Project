package org.example.oopjavagroupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.oopjavagroupproject.database.DatabaseHandler;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            if (dbHandler.getDbConnection() != null) {
                System.out.println("✅ SUCCESS: Connection to PostgreSQL established!");
            }
        } catch (Exception e) {
            System.err.println("❌ DATABASE ERROR: " + e.getMessage());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Project: Working with Databases");
        stage.setScene(scene);
        stage.show();
    }
}
