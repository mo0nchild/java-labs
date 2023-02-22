package task.execute;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class RunTask7 extends Application {

    public static void main(String[] args) { Application.launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        var fxml_node = RunTask7.class.getResource("/lab/solution7/views/LabTask7View.fxml");
        var parent = FXMLLoader.load(Objects.requireNonNull(fxml_node));

        stage.setScene(new Scene((Parent)parent));
        stage.setResizable(false);
        stage.setTitle("Лаюораторная работа 7"); stage.show();
    }
}
