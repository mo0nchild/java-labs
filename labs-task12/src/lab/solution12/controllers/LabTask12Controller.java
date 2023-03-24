package lab.solution12.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import labs.helper.types.LabTaskException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public final class LabTask12Controller implements Initializable {
    private final LabTask12Logic labLogic = new LabTask12Logic(this::drawFigureHandler);
    @FXML private Slider sizeSlider, lengthSizeSlider;
    @FXML private Canvas graphicPane;
    @FXML private Spinner<Integer> recursiveSpinner;
    @FXML private AnchorPane rootElement;

    private final void drawFigureHandler(Point2D position, Integer size) {
        var context = this.graphicPane.getGraphicsContext2D();
        context.setStroke(Color.BLACK);
        context.strokeOval(position.getX() - size / 2.0, position.getY() - size / 2.0, size, size);
    }

    public void userInputHandler(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {
        var begin_point = new Point2D(this.graphicPane.getWidth() / 2,
                this.graphicPane.getHeight() / 2);
        this.graphicPane.getGraphicsContext2D().clearRect(0, 0, this.graphicPane.getWidth(), this.graphicPane.getHeight());
        try {
            this.labLogic.calculateTask1(this.recursiveSpinner.getValue(), begin_point, (int)this.sizeSlider.getValue(),
                    this.lengthSizeSlider.getValue());
        } catch (LabTaskException error) {
            (new Alert(Alert.AlertType.ERROR, error.getMessage())).showAndWait();
        }
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        this.sizeSlider.valueProperty().addListener(this::userInputHandler);
        this.lengthSizeSlider.valueProperty().addListener(this::userInputHandler);
        this.recursiveSpinner.valueProperty().addListener(this::userInputHandler);
    }
}
