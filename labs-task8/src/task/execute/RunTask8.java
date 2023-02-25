package task.execute;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class RunTask8 extends Application {

    public static void main(String[] args) { Application.launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        var fxml_node = RunTask8.class.getResource("/lab/solution8/views/LabTask8View.fxml");
        var parent = FXMLLoader.load(Objects.requireNonNull(fxml_node));

        stage.setScene(new Scene((Parent)parent));
        stage.setResizable(false);
        stage.setTitle("Лабораторная работа 8"); stage.show();
    }
}
