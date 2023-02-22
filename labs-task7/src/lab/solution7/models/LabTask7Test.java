package lab.solution7.models;

import labs.helper.types.LabTaskException;
import labs.helper.types.LabTaskTest;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class LabTask7Test implements LabTaskTestable {
    private final List<Integer> valuesList;
    private final Integer resultValue, result2Value, nValue;
    public final List<Integer> getValuesList() { return this.valuesList; }
    public final Integer getNValue() { return this.nValue; }
    public final Integer getResultValue() { return this.resultValue; }
    public final Integer getResult2Value() { return this.result2Value; }

    public LabTask7Test(int result, int result2, int n, Integer... values) { super();
        this.valuesList = new ArrayList<Integer>(Arrays.stream(values).toList());
        this.resultValue = result; this.result2Value = result2; this.nValue = n;
    }

    public static List<? extends LabTaskTestable> buildTestList() throws LabTaskException {
        try (var factory = new LabTaskTestFactory()) {
            return new ArrayList<LabTaskTestable>(factory.buildTestList().getBufferList());
        }
        catch (Exception error) {
            throw new LabTaskException(error.getMessage(), LabTask7Test.class);
        }
    }
    @Override
    public final String toString() { return "Тест с результатом: " + this.resultValue
            + "; " + this.result2Value; }
}

final class LabTaskTestFactory implements Closeable {
    private final List<LabTaskTestable> bufferList;

    public List<LabTaskTestable> getBufferList() { return this.bufferList; }
    public LabTaskTestFactory() { super(); bufferList = new ArrayList<>(); }
    public LabTaskTestFactory buildTestList() throws Exception {
        this.bufferList.clear();
        try {
            this.bufferList.add(new LabTask7Test(-1, 3, 2, -10, 3, 100, 7, -10, 28));
            this.bufferList.add(new LabTask7Test(3, 6, 1, 40, 60, -104, 0, 20, 100, -2));

            this.bufferList.add(new LabTask7Test(7, 7, 3, -10, 3, 2, 5, 1, -1, 5, 5, 2));
        }
        catch (RuntimeException error) { throw new Exception(error.getMessage()); }
        return this;
    }
    @Override
    public void close() throws IOException { bufferList.clear(); }
}
