package task.execute;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class RunTask10 extends Application {

    public static void main(String[] args) { Application.launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        var fxml_node = RunTask10.class.getResource("/lab/solution10/views/LabTask10View.fxml");
        var parent = FXMLLoader.load(Objects.requireNonNull(fxml_node));

        stage.setScene(new Scene((Parent)parent));
        stage.setResizable(false);
        stage.setTitle("Лабораторная работа 10"); stage.show();
    }
}
