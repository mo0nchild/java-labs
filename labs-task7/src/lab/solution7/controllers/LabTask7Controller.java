package lab.solution7.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lab.solution7.models.LabTask7Test;
import labs.helper.types.LabTaskException;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

final class TableValuesType implements Serializable {
    private final IntegerProperty value, position;
    public TableValuesType(int value, int position) {
        this.position = new SimpleIntegerProperty(position);
        this.value = new SimpleIntegerProperty(value);
    }
    @Override
    public String toString() { return "#" + position.get() + "; значение: " + value.get(); }

    public void setValue(int value) { this.value.set(value); }
    public int getValue() { return this.value.get(); }

    public void setPosition(int value) { this.position.set(value); }
    public int getPosition() { return this.position.get(); }
}

final public class LabTask7Controller implements Initializable {
    private LabTask7Logic labLogic = new LabTask7Logic(new Integer[]{ });
    @FXML private ListView<TableValuesType> valuesListView;
    @FXML private TextField resultTextField, testTextField, result2TextField,
            test2TextField, testNTextField;
    @FXML private Button calculateButton, addButton, clearButton;
    @FXML private ComboBox<LabTask7Test> testComboBox;
    @FXML private Spinner<Integer> newValueSpinner, nValueSpinner;

    private static void initializeHandlers(LabTask7Controller controller) {
        controller.clearButton.setOnMouseClicked(controller::clearButtonHandler);

        controller.calculateButton.setOnMouseClicked(controller::calculateButtonHandler);
        controller.addButton.setOnMouseClicked(controller::addButtonHandler);
    }

    private final void clearButtonHandler(MouseEvent event) {
        this.valuesListView.getItems().clear();
        this.testTextField.setText(""); this.test2TextField.setText("");
    }
    private final void addButtonHandler(MouseEvent event) {
        var current_pos = this.valuesListView.getItems().size() + 1;
        var new_value = new TableValuesType(this.newValueSpinner.getValue(), current_pos);

        this.valuesListView.getItems().add(new_value);
    }
    private final void calculateButtonHandler(MouseEvent event) {
        var input_list = this.valuesListView.getItems().stream()
                .map(TableValuesType::getValue).toList();

        this.labLogic = new LabTask7Logic(input_list.toArray(Integer[]::new));
        try {
            var result1 = this.labLogic.calculateTask1(this.nValueSpinner.getValue());
            var result2 = this.labLogic.calculateTask2(this.nValueSpinner.getValue());

            this.resultTextField.setText(String.valueOf(result1));
            this.result2TextField.setText(String.valueOf(result2));

            if(this.testTextField.getText().equals("") || this.test2TextField.getText().equals("")
                || this.nValueSpinner.getValue() != Integer.parseInt(this.testNTextField.getText())) return;
            Alert message_box = null;
            if(this.test2TextField.getText().equals(this.result2TextField.getText())
                    && this.testTextField.getText().equals(this.resultTextField.getText())) {

                message_box = new Alert(Alert.AlertType.CONFIRMATION, "Тесты выполнены");
            }
            else message_box = new Alert(Alert.AlertType.WARNING, "Тесты провалены");
            message_box.showAndWait();
        }
        catch (LabTaskException error) {
            var message_box = new Alert(Alert.AlertType.WARNING, error.getMessage());
            message_box.showAndWait();
        }
    }
    @FXML @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        LabTask7Controller.initializeHandlers(this);

        try { for(var item : LabTask7Test.buildTestList()) { this.testComboBox.getItems()
                .add((LabTask7Test)item); } }
        catch (LabTaskException error) { System.out.println(error.getMessage()); }

        this.testComboBox.valueProperty().addListener((obj, oldValue, newValue) -> {
            this.valuesListView.getItems().clear();
            for(int index = 0; index < newValue.getValuesList().size(); index++) {
                var item = newValue.getValuesList().get(index);
                this.valuesListView.getItems().add(new TableValuesType(item, index + 1));
            }
            testTextField.setText(String.valueOf(newValue.getResultValue()));
            test2TextField.setText(String.valueOf(newValue.getResult2Value()));
            testNTextField.setText(String.valueOf(newValue.getNValue()));
        });
        this.testComboBox.getSelectionModel().select(0);
    }
}
