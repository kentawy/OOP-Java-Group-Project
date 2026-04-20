package org.example.oopjavagroupproject;

import javafx.application.Application;

/**
 * A launcher class to start the JavaFX application.
 * This is a workaround for some IDEs that have issues with launching JavaFX applications
 * that extend the {@link Application} class directly.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class Launcher {
    /**
     * The main method that launches the JavaFX application.
     *
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
