package lab.solution7.controllers;

import labs.helper.types.LabTaskException;
import java.util.Arrays;
import java.util.Comparator;

public class LabTask7Logic {
    private final Integer[] inputValues;

    public LabTask7Logic(Integer[] input_values) {
        this.inputValues = input_values;
    }

    public Integer[] getInputValues() { return this.inputValues; }

    public int calculateTask1(int n) throws LabTaskException {
        if(n * 2 > this.inputValues.length) return -1;
        var max_index_op = Arrays.stream(this.inputValues).max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) { return o1.compareTo(o2); }
        });
        if(max_index_op.isEmpty()) { throw new LabTaskException("Неверные параметры", this.getClass()); }

        int max_index = Arrays.stream(this.inputValues).toList().lastIndexOf(max_index_op.get()),
                result = Math.min(max_index, n * 2 - 1) + 1;
        if(Math.abs(max_index - (n * 2 - 1)) <= 1) return -1;

        for(int index = result; index < Math.max(max_index, n * 2 - 1); index++) {
            if(this.inputValues[index] < this.inputValues[result]) result = index;
        }
        return result + 1;
    }

    public int calculateTask2(int n) throws LabTaskException {
        if(n > this.inputValues.length) return -1;
        if(n < 0) throw new LabTaskException("Неверные параметры", this.getClass());

        int result_value = Integer.MIN_VALUE, result_index = 0;
        for(int index = 0; index < this.inputValues.length - n + 1; index++) {

            var current = Arrays.stream(this.inputValues).skip(index)
                    .limit(n).reduce(Integer::sum);
            if (current.isEmpty()) throw new LabTaskException("Ошибка вычисления №2", this.getClass());

            if(result_value < current.get()) {
                result_value = current.get(); result_index = index;
            }
        }
        return result_index + 1;
    }
}
