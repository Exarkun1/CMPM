package com.propcool.cmpm_project;

import com.propcool.cmpm_project.analysing.build.DifBuilder;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.functions.combination.Division;
import com.propcool.cmpm_project.functions.combination.Multiply;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CmpmApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CmpmApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("CMPM");
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        /*Function f = new Difference(new FunctionDecoratorX(), new Multiply(new FunctionDecoratorY(), new FunctionDecoratorY()));
        Function dif = new DifBuilder().difY(f);
        System.out.println(dif.get(-1, -1));
        System.out.println(dif.get(-1, -5));
        System.out.println(dif.get(0, -2));
        System.out.println(dif.get(0, -1));
        System.exit(0);*/
    }
}