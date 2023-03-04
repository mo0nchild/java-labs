package lab.solution9.controllers;

import labs.helper.types.LabTaskException;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LabTask9Logic implements Serializable {
    public LabTask9Logic() { super(); }

    private boolean copy(List<Integer> src, int src_i, List<Integer> dst, int dst_i, int count) {
        if(src_i < 0 || dst_i < 0) return false;
        try {
            if(dst_i >= dst.size()) dst_i = dst.size() - 1;
            for(int i = 0; i < (src_i + count >= src.size() ? src.size() - src_i : count); i++) {
                dst.add(dst_i + i, src.get(i + src_i));
            }
        }
        catch (Exception error) { System.out.println(error.getMessage()); return false; }
        return true;
    }
    private final List<Integer> sort(@NotNull List<Integer> input_list) {
        for(int index = 0; index < input_list.size() - 1; index++) {
            for(int k = index + 1; k < input_list.size(); k++) {

                if(input_list.get(index) > input_list.get(k)) {
                    var buffer_value = input_list.get(index);
                    input_list.set(index, input_list.get(k)); input_list.set(k, buffer_value);
                }
            }
        }
        return input_list;
    }
    public List<Integer> calculateTask1(List<Integer> list1, List<Integer> list2)
            throws LabTaskException {
        final var list1_copy = new ArrayList<>(list1);

        if(!this.copy(list2, 0, list1_copy, list1_copy.size(), list2.size() + 1)){
            throw new LabTaskException("Неверные входные параметры", this.getClass());
        }
        for(var item : list1_copy) System.out.println(item);
        System.out.println();
        return this.sort(list1_copy);
    }

    public List<Integer> calculateTask2(List<Integer> list) throws LabTaskException {
        final var list_copy = new ArrayList<>(list);
        try {
            for(int i = 0; i < list_copy.size() - 1; i++) {
                if(list_copy.get(i).equals(list_copy.get(i + 1))) { list_copy.remove(i + 1); i--; }
            }
        } catch (Exception error) { throw new LabTaskException(error.getMessage(), this.getClass()); }
        return list_copy;
    }

}
