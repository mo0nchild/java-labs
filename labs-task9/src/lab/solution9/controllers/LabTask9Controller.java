package lab.solution9.controllers;

import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public final class LabTask9Controller implements Initializable {
    private final LabTask9Logic labLogic = new LabTask9Logic();

    public final ObservableList<Integer> list1 = FXCollections.observableArrayList(),
            list2 = FXCollections.observableArrayList(), list3 = FXCollections.observableArrayList();

    @FXML private ListView<String> input1ListView, input2ListView, input3ListView;
    @FXML private Button fileReadButton, clear3Button, add3Button, clear2Button,
            add2Button, clear1Button, add1Button, calculateButton, fileWriteButton;
    @FXML private Spinner<Integer> value1Spinner, value2Spinner, value3Spinner;
    @FXML private TextField filePathTextField;
    @FXML private ListView<String> taskInput11ListView, taskInput12ListView, taskOutput1ListView,
            taskInput2ListView, taskOutput2ListView;
    @FXML private AnchorPane rootElement;

    public LabTask9Controller() { super(); }

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

    private final void fileReadHandler(MouseEvent event) {
        this.fileDialogHandler((String filepath) -> {
            try {
                final var file_data = LabTask9Filesystem.fileDataRead(filepath);
                list1.clear(); list1.addAll(file_data.list1());
                list2.clear(); list2.addAll(file_data.list2());
                list3.clear(); list3.addAll(file_data.list());
            }
            catch (IOException error) { new Alert(Alert.AlertType.ERROR, error.getMessage()).showAndWait(); }
        });
    }
    private final void fileWriteHandler(MouseEvent event) {
        this.fileDialogHandler((String filepath) -> {
            try {
                LabTask9Filesystem.fileDataWrite(filepath, new LabTask9Filesystem.FileData(
                    this.list1, this.list2, this.list3));
            }
            catch (IOException error) { new Alert(Alert.AlertType.ERROR, error.getMessage()).showAndWait(); }
        });
    }
    private final void calculateButtonHandler(MouseEvent event) {
        this.taskOutput1ListView.getItems().clear();
        this.taskOutput2ListView.getItems().clear();
        try {
            final var result1 = this.labLogic.calculateTask1(this.list1, this.list2);
            final var result2 = this.labLogic.calculateTask2(this.list3);

            for(int index = 0; index < result1.size(); index++) {
                this.taskOutput1ListView.getItems().add("Результат #" + (index + 1) + ": " + result1.get(index));
            }
            for(int index = 0; index < result2.size(); index++) {
                this.taskOutput2ListView.getItems().add("Результат #" + (index + 1) + ": " + result2.get(index));
            }
        }
        catch (LabTaskException error) { new Alert(Alert.AlertType.ERROR, error.getMessage()).showAndWait(); }
    }

    private void updateListViewHandler(ListView<? super String> listview, List<Integer> list) {
        listview.getItems().clear();
        for(int index = 0; index < list.size(); index++) {
            listview.getItems().add("Значение #" + (index + 1) + ": " + list.get(index));
        }
    }

    @FXML @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {

        this.list1.addListener((ListChangeListener<? super Integer>) (event) -> {
            this.updateListViewHandler(this.input1ListView, this.list1);
            this.updateListViewHandler(this.taskInput11ListView, this.list1);
        });
        this.list2.addListener((ListChangeListener<? super Integer>) (event) -> {
            this.updateListViewHandler(this.input2ListView, this.list2);
            this.updateListViewHandler(this.taskInput12ListView, this.list2);
        });
        this.list3.addListener((ListChangeListener<? super Integer>) (event) -> {
            this.updateListViewHandler(this.input3ListView, this.list3);
            this.updateListViewHandler(this.taskInput2ListView, this.list3);
        });
        this.initializeHandlers();
    }
    private final void initializeHandlers() {
        this.add1Button.setOnMouseClicked((event) -> this.list1.add(this.value1Spinner.getValue()));
        this.add2Button.setOnMouseClicked((event) -> this.list2.add(this.value2Spinner.getValue()));
        this.add3Button.setOnMouseClicked((event) -> this.list3.add(this.value3Spinner.getValue()));

        this.clear1Button.setOnMouseClicked((event) -> this.list1.clear());
        this.clear2Button.setOnMouseClicked((event) -> this.list2.clear());
        this.clear3Button.setOnMouseClicked((event) -> this.list3.clear());

        this.fileReadButton.setOnMouseClicked(this::fileReadHandler);
        this.fileWriteButton.setOnMouseClicked(this::fileWriteHandler);
        this.calculateButton.setOnMouseClicked(this::calculateButtonHandler);
    }
}
