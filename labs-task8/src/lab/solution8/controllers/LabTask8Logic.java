package lab.solution8.controllers;

import labs.helper.types.LabTaskException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

enum DirectionType implements Serializable { HORIZONTAL, VERTICAL }
public class LabTask8Logic implements Serializable {
    private static final int DEFAULT_ROW = 5, DEFAULT_COLUMN = 5;
    private Integer[][] taskData = new Integer[DEFAULT_ROW][DEFAULT_COLUMN];
    private Integer rowCount, columnCount;

    public Integer[][] getTaskData() { return this.taskData; }

    public Integer getRowCount() { return this.rowCount; }
    public Integer getColumnCount() { return this.columnCount; }

    public LabTask8Logic(Integer row_count, Integer column_count) { super();
        this.columnCount = column_count; this.rowCount = row_count;

        for(int row = 0; row < this.rowCount; row++){
            for(int col = 0; col < this.columnCount; col++) this.taskData[row][col] = 0;
        }
    }
    public LabTask8Logic() { this(LabTask8Logic.DEFAULT_ROW, LabTask8Logic.DEFAULT_COLUMN); }

    public final void fileDataWrite(final String filepath, @NotNull Integer[][] data) throws IOException {
        if(data.length == 0 || data[0].length == 0 || filepath == null) throw new IOException("Нет данных");

        var file_descriptor = new File(filepath);
        if (!file_descriptor.exists()) {
            if (!file_descriptor.createNewFile()) throw new IOException("Файл не подготовлен");
        }
        try (var file_stream = new DataOutputStream(new FileOutputStream(filepath))) {
            file_stream.writeInt(data.length); file_stream.writeInt(data[0].length);
            for (Integer[] datum : data) {
                for (Integer integer : datum) file_stream.writeInt(integer);
            }
        }
        catch (IOException error) { System.out.println(error.getMessage()); }
    }
    public final Integer[][] fileDataRead(final String filepath) throws IOException {
        Integer[][] file_result = null;
        if(!new File(filepath).exists()) throw new IOException("Файл не найден");

        try (var file_stream = new DataInputStream(new FileInputStream(filepath))) {
            this.rowCount = file_stream.readInt(); this.columnCount = file_stream.readInt();

            file_result = new Integer[this.rowCount][this.columnCount];
            for(int row = 0; row < rowCount; row++) {
                for(int col = 0; col < columnCount; col++) file_result[row][col] = file_stream.readInt();
            }
        }
        catch (IOException error) { System.out.println(error.getMessage()); }
        return (this.taskData = file_result);
    }

    protected static Boolean checkBetween(double val, int b, int e) { return (val <= e) && (val >= b); }
    public Integer calculateTask1() throws LabTaskException {
        int task_result = 0, size = this.columnCount - 1;
        if(!Objects.equals(this.rowCount, this.columnCount) || columnCount == 0 || rowCount == 0) {
            throw new LabTaskException("Поле не соотвествует шаблону", this.getClass());
        }
        int main_diagonal = (checkBetween(this.taskData[0][0], 0, 1)) ? this.taskData[0][0] : -1,
                sec_diagonal = checkBetween(this.taskData[0][size], 0, 1) ? this.taskData[0][size] : -1;

        for(int index = 0; index < this.rowCount; index++) {
            int horizon = this.taskData[index][0], vertical = this.taskData[0][index];

            if(main_diagonal != this.taskData[index][index] && main_diagonal != -1) main_diagonal = -1;
            if(sec_diagonal != this.taskData[index][size - index] && sec_diagonal != -1) sec_diagonal = -1;

            if(!checkBetween(horizon, 0, 1) && !checkBetween(vertical, 0, 1)) continue;
            for(int dash = 1; dash < this.columnCount; dash++) {

                if(horizon != this.taskData[index][dash] && horizon != -1) horizon = -1;
                if(vertical != this.taskData[dash][index] && vertical != -1) vertical = -1;
            }
            var zero_line_winner = (horizon == 0 || vertical == 0);
            var one_line_winner = (horizon == 1 || vertical == 1);

            var iteration_result = (one_line_winner) ? 1 : -1;
            if(zero_line_winner != one_line_winner) {

                if(iteration_result != task_result && task_result != 0) return 0;
                task_result = iteration_result;
            }
            else if(zero_line_winner || one_line_winner) return 0;
        }
        if(main_diagonal == -1 && sec_diagonal == -1) return task_result;
        return ((main_diagonal != -1 ? main_diagonal : sec_diagonal) == 0) ? -1 : 1;
    }

    public Integer[][] calculateTask2(int dash_value, DirectionType direction)
            throws LabTaskException {
        var buffer = new Integer[(direction == DirectionType.HORIZONTAL) ? rowCount : columnCount];

        for(int index = 0; index < dash_value; index++) {

        }
        return null;
    }
}
