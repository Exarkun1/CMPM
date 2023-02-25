module com.propcool.cmpm_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.propcool.cmpm_project to javafx.fxml;
    exports com.propcool.cmpm_project;
    exports com.propcool.cmpm_project.controllers;
    opens com.propcool.cmpm_project.controllers to javafx.fxml;
}