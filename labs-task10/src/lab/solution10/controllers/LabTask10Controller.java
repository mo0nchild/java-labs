package lab.solution10.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import labs.helper.types.LabTaskException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public final class LabTask10Controller implements Initializable {
    private final LabTask10Logic labLogic = new LabTask10Logic();
    @FXML private ListView<String> dataInputListView, dataSortedListView;
    @FXML private Button fileLoadButton;
    @FXML private ComboBox<String> modesComboBox;
    @FXML private AnchorPane rootElement;

    public LabTask10Controller() { super(); }

    private void fileDialogHandler(Consumer<String> consumer) {
        var file_dialog = new FileChooser(); file_dialog.setTitle("Выберите файл для сохранения");
        Alert message_box = null;
        file_dialog.setInitialDirectory(new File(System.getProperty("user.dir")));

        var file_instance = file_dialog.showOpenDialog(this.rootElement.getScene().getWindow());
        if(file_instance == null) return;
        try {
            consumer.accept(file_instance.getAbsolutePath());
            message_box = new Alert(Alert.AlertType.CONFIRMATION, "Данные обработаны");
        }
        catch (Exception error) { message_box = new Alert(Alert.AlertType.WARNING, error.getMessage()); }
        message_box.showAndWait();
    }

    private List<FilesystemObject> fileData = new ArrayList<>();
    private final void dataLoadButtonHandle(ActionEvent event) {
        this.fileDialogHandler((String file_path) -> {
            var builder_type =
                    (this.modesComboBox.getSelectionModel().getSelectedIndex() == 0)
                    ? new LabTask10Models.Triangle.TriangleBuilder()
                    : new LabTask10Models.Student.StudentBuilder();
            try {
                this.fileData = LabTask10Filesystem.fileDataRead(file_path, builder_type);
            }
            catch (IOException error) { throw new RuntimeException(error.getMessage()); }
        });
        this.dataInputListView.getItems().clear();
        for(var triangle_item : this.fileData) {
            this.dataInputListView.getItems().add(triangle_item.toString());
        }
        var result = this.fileData.toArray(FilesystemObject[]::new);
        try {
            switch(this.modesComboBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    var triangles_input = Arrays.stream(result).toArray(LabTask10Models.Triangle[]::new);
                    this.labLogic.calculateTask1(triangles_input);
                    result = triangles_input; break;
                case 1:
                    var student_input = Arrays.stream(result).toArray(LabTask10Models.Student[]::new);
                    this.labLogic.calculateTask2(student_input);
                    result = student_input; break;
            }
        }
        catch (LabTaskException error) { throw new RuntimeException(error.getMessage()); }

        this.dataSortedListView.getItems().clear();
        for(var triangle_item : result) {
            this.dataSortedListView.getItems().add(triangle_item.toString());
        }
    }

    @FXML @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        this.modesComboBox.getItems().add("Режим треугольников");
        this.modesComboBox.getItems().add("Режим студентов");
        this.modesComboBox.getSelectionModel().select(0);
        this.fileLoadButton.setOnAction(this::dataLoadButtonHandle);
    }

}
