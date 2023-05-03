package com.propcool.cmpm_project;

import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.NamedFunction;
import com.propcool.cmpm_project.auxiliary.DifBuilder;
import com.propcool.cmpm_project.auxiliary.RootSearcher;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;
import com.propcool.cmpm_project.functions.basic.Pow;
import com.propcool.cmpm_project.functions.basic.VariableX;
import com.propcool.cmpm_project.functions.basic.VariableY;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.functions.combination.Multiply;
import com.propcool.cmpm_project.functions.combination.Sum;
import com.propcool.cmpm_project.functions.mono.Cos;
import com.propcool.cmpm_project.functions.mono.Sin;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.simple.SimpleBuilder;
import javafx.application.Application;
import javafx.application.Platform;
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
        Function f = new SimpleBuilder().build("0=x^2+y^2-1");
        Function g = new SimpleBuilder().build("0=x^2+y^2+x*y+2x+y");
        RootSearcher rs = new RootSearcher(0.001);
        System.out.println(rs.intersectionXY(f, g, -1, 1));
        Platform.exit();
    }
}