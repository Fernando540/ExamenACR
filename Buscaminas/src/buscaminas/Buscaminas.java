/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Yael
 */
public class Buscaminas extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setScene(scene);
        stage.setTitle("Buscaminas");
        stage.centerOnScreen();
        Image icono = new Image("images/titleBuscIcon.png");
        stage.getIcons().add(icono);
        stage.initStyle(StageStyle.UNIFIED);
        stage.setResizable(false);
        stage.show();
        System.out.println("");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
