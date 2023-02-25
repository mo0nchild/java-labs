package lab.solution8.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import labs.helper.types.LabTaskException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

final public class LabTask8Controller implements Initializable {
    private final LabTask8Logic labLogic = new LabTask8Logic();
    @FXML private  AnchorPane rootElement;
    @FXML private ListView<String> rowEditListView;
    @FXML private ToggleGroup group;
    @FXML private RadioButton horizontalToggleButton, verticalToggleButton;
    @FXML private  Spinner<Integer> dashValueSpinner, editTableSpinner;
    @FXML private TextField result1TextField;
    @FXML private Button calculate1Button, calculate2Button, editTableButton,
            loadTableButton, saveTableButton;
    @FXML private TableView<Integer[]> dataTableView;

    private static void tableValuesUpdate(TableView<Integer[]> tableView, Integer[][] values) {
        tableView.getItems().clear(); tableView.getColumns().clear();
        for(int index = 0; index < values[0].length; index++) {

            var table_column = new TableColumn<Integer[], Integer>("Col" + String.valueOf(index + 1));
            table_column.setResizable(false); table_column.setPrefWidth(60);

            final int n = index;
            table_column.setCellValueFactory((param) -> new ObservableValueBase<Integer>() {
                @Override
                public Integer getValue() { return param.getValue()[n]; }
            });
            tableView.getColumns().add(table_column);
        }
        for(var item : values) tableView.getItems().add(item);
    }

    private void dataTableViewHandler(Integer index) {
        this.rowEditListView.getItems().clear();
        var row_items = this.dataTableView.getItems().get(index);

        for(int col = 0; col < row_items.length; col++) {
            this.rowEditListView.getItems().add("Col" + String.valueOf(col + 1) + ": " + row_items[col]);
        }
    }

    private void calculate2ButtonHandler(MouseEvent mouseEvent) {
        var direction = this.horizontalToggleButton.selectedProperty().get()
                ? DirectionType.HORIZONTAL : DirectionType.VERTICAL;

        try { this.labLogic.calculateTask2(this.dashValueSpinner.getValue(), direction); }
        catch (LabTaskException error) { new Alert(Alert.AlertType.WARNING, error.getMessage()); }

        LabTask8Controller.tableValuesUpdate(this.dataTableView, this.labLogic.getTaskData());
    }
    private void calculate1ButtonHandler(MouseEvent mouseEvent) {
        String task_result = null;
        try {
            task_result = switch(this.labLogic.calculateTask1()) {
                case 1 -> "Единички победили "; case -1 -> "Нолики победили"; case 0 -> "Ничья";
                default -> throw new IllegalStateException("Unexpected value: " + this.labLogic.calculateTask1());
            };
        } catch (LabTaskException error) { new Alert(Alert.AlertType.WARNING, error.getMessage()); }
        this.result1TextField.setText(task_result);
    }

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

    private void saveTableButtonHandler(MouseEvent mouseEvent) {
        this.fileDialogHandler((String file_path) -> {
            try { this.labLogic.fileDataWrite(file_path, this.labLogic.getTaskData()); }
            catch (IOException error) { throw new RuntimeException(error); }
        });
    }
    private void loadTableButtonHandler(MouseEvent mouseEvent) {
        this.fileDialogHandler((String file_path) -> {
            try { LabTask8Controller.tableValuesUpdate(this.dataTableView, labLogic.fileDataRead(file_path)); }
            catch (IOException error) { throw new RuntimeException(error); }
        });
    }

    private void editTableButtonHandler(MouseEvent event) {
        var col_index = this.rowEditListView.getSelectionModel().getSelectedIndex();
        var row_index = this.dataTableView.getSelectionModel().getSelectedIndex();
        if(row_index == -1 || col_index == -1) return;

        this.labLogic.getTaskData()[row_index][col_index] = this.editTableSpinner.getValue();
        this.dataTableView.getSelectionModel().clearSelection();

        LabTask8Controller.tableValuesUpdate(this.dataTableView, this.labLogic.getTaskData());
        this.dataTableViewHandler(row_index);
    }

    @FXML @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        LabTask8Controller.tableValuesUpdate(this.dataTableView, this.labLogic.getTaskData());
        LabTask8Controller.initializeHandlers(this);

        this.horizontalToggleButton.selectedProperty().set(true);
    }

    private static void initializeHandlers(LabTask8Controller controller) {
        controller.editTableButton.setOnMouseClicked(controller::editTableButtonHandler);
        controller.loadTableButton.setOnMouseClicked(controller::loadTableButtonHandler);
        controller.saveTableButton.setOnMouseClicked(controller::saveTableButtonHandler);

        controller.calculate1Button.setOnMouseClicked(controller::calculate1ButtonHandler);
        controller.calculate2Button.setOnMouseClicked(controller::calculate2ButtonHandler);

        controller.dataTableView.getSelectionModel().selectedIndexProperty().addListener(
            (obs, oldValue, newValue) -> {
                if(newValue.intValue() == -1) return;
                controller.dataTableViewHandler(newValue.intValue());
            }
        );
    }
}
