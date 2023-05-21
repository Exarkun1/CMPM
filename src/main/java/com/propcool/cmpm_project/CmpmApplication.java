package com.propcool.cmpm_project;

import com.propcool.cmpm_project.functions.interpolate.PolynomialWithX;
import com.propcool.cmpm_project.util.TabulateBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CmpmApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CmpmApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CMPM");
        stage.getIcons().add(new Image(Objects.requireNonNull(CmpmApplication.class.getResourceAsStream("Icon.png"))));
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        /*PolynomialWithX p = new TabulateBuilder().approximation(new double[]{0, 1, 2, 3, 4, 5}, new double[] {-2, -1, 1, 3, 2, 4}, 2);
        System.out.println(p.get(1));
        Platform.exit();*/
    }
}