package lab.solution5;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import labs.helper.types.LabTaskException;

interface VisualLayerDrawable { public char create_layer(int x); }

public class LabTask5 {
    private final int w_value, h_value, s_value;
    private boolean layer_switcher = false;

    public LabTask5() throws LabTaskException { this(13, 9, 2); }
    public LabTask5(int w_param, int h_param, int s_param) throws LabTaskException { super();

        if(w_param < 1 || h_param < 1 || s_param < 1 || s_param % 2 == 0) {
            throw new LabTaskException("Неверные параметры", this.getClass());
        }
        this.w_value = w_param; this.h_value = h_param; this.s_value = s_param;
    }

    private void drawSquare(PrintStream printer) {
        var space = 1;
        var switcher = false;

        for(var i = 0; i < this.s_value; i++) {
            for(int x = 0; x < this.s_value; x++) {
                if(x >= (s_value - space) / 2 && x < space + (s_value - space) / 2 ) printer.print('*');
                else printer.print(' ');
            }
            printer.println();
            if(space == this.s_value) switcher = true;
            space = (switcher) ? space - 2 : space + 2;
        }
    }

    private void drawChess(PrintStream printer) {
        this.drawLayer(printer, (int x) -> { return '-'; }, '+');

        boolean y_switcher = false;
        for(int y = 0; y < this.h_value - 2; y++) {

            if((y + 1) % this.s_value == 0) y_switcher = !y_switcher;
            this.drawLayer(printer, (int x) -> {
                if((x + 1) % this.s_value == 0) this.layer_switcher = !layer_switcher;

                return (this.layer_switcher) ? '*' : ' ';
            }, '|');
            this.layer_switcher = y_switcher;
        }
        this.drawLayer(printer, (int x) -> { return '-'; }, '+');
    }

    public String generateVisual(TaskType type) {
        var buffer_result = new ByteArrayOutputStream(this.h_value * this.w_value);

        try (var printer = new PrintStream(buffer_result)) {
            if (type == null) return "";

            switch(type) {
                case SQUARE_TASK -> this.drawSquare(printer);
                case CHESS_TASK -> this.drawChess(printer);
            }
        }
        catch (Exception error) { System.out.println(error.getMessage()); return null; }

        return new String(buffer_result.toString());
    }

    void drawLayer(PrintStream printer, VisualLayerDrawable layer_drawer, char border) {
        printer.print(border);

        for(int i = 0; i < this.w_value - 2; i++) printer.print(layer_drawer.create_layer(i - 1));
        printer.println(border);
    }
}
