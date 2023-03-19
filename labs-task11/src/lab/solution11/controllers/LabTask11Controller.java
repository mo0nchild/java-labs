package lab.solution11.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import labs.helper.types.LabTaskException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public final class LabTask11Controller implements Initializable {
    private final LabTask11Logic labLogic = new LabTask11Logic();
    @FXML private TextArea inputTextArea, outputTextArea;
    @FXML private Button loadTextButton, convertTextButton, findWordsButton;
    @FXML private TabPane taskTabPane;
    @FXML private ListView<String> wordsListView;
    @FXML private Spinner<Integer> wordLengthSpinner;
    @FXML private AnchorPane rootElement;

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

    private final void loadTextButtonHandler(ActionEvent actionEvent) {
        this.fileDialogHandler((String filepath) -> {
            try { this.inputTextArea.setText(LabTask11Filesystem.fileDataRead(filepath)); }
            catch (IOException error) {
                (new Alert(Alert.AlertType.ERROR, error.getMessage())).showAndWait();
            }
        });
    }

    private final void findWordsButtonHandler(ActionEvent actionEvent) {
        String[] result_task = null;
        try {
            result_task = this.labLogic.calculateTask2(this.inputTextArea.getText(),
                    this.wordLengthSpinner.getValue());

        } catch (LabTaskException error) {
            (new Alert(Alert.AlertType.ERROR, error.getMessage())).showAndWait();
            return;
        }
        this.wordsListView.getItems().clear();
        for(var item : result_task) this.wordsListView.getItems().add(item);
    }

    private final void convertTextButtonHandler(ActionEvent actionEvent) {
        try { this.outputTextArea.setText(this.labLogic.calculateTask1(this.inputTextArea.getText())); }
        catch (LabTaskException error) {
            (new Alert(Alert.AlertType.ERROR, error.getMessage())).showAndWait();
        }
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadTextButton.setOnAction(this::loadTextButtonHandler);
        this.findWordsButton.setOnAction(this::findWordsButtonHandler);
        this.convertTextButton.setOnAction(this::convertTextButtonHandler);

    }
}
