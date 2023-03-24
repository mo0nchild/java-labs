package lab.solution12.controllers;

import javafx.geometry.Point2D;
import labs.helper.types.LabTaskException;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public final class LabTask12Logic implements Serializable {
    public static interface GraphicDrawable {
        public abstract void drawFigure(Point2D position, Integer size);
    }
    @NotNull private final GraphicDrawable graphic;
    public final @NotNull GraphicDrawable getGraphic() { return this.graphic; }

    public LabTask12Logic(@NotNull GraphicDrawable graphic) { super(); this.graphic = graphic; }

    public void calculateTask1(Integer level,  Point2D position, Integer size, double a) throws LabTaskException {
        if(level == 0) return;

        var left_point = new Point2D(position.getX() - (a), position.getY());
        var right_point = new Point2D(position.getX() + (a), position.getY());

        var top_point = new Point2D(position.getX(), position.getY() - (a));
        var bottom_point = new Point2D(position.getX(), position.getY() + (a));

        this.calculateTask1(level - 1, left_point, size / 2, (a * 0.5) - size * 0.25);
        this.calculateTask1(level - 1, right_point, size / 2, (a * 0.5) - size * 0.25);

        this.calculateTask1(level - 1, top_point, size / 2, (a * 0.5) - size * 0.25);
        this.calculateTask1(level - 1, bottom_point, size / 2, (a * 0.5) - size * 0.25);
        this.graphic.drawFigure(position, size);
    }
}
