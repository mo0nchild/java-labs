package task.execute;

import lab.solution5.LabTask5;
import lab.solution5.TaskType;
import labs.helper.types.LabTaskRunnable;

import java.util.Scanner;
import java.util.ServiceLoader;

public class RunTask5 {

    public static void main(String[] args) {
        Iterable<LabTaskRunnable> services =
                ServiceLoader.load(LabTaskRunnable.class);

        for(var item : services) {
            try { item.runTask(); }
            catch (Exception error) { System.out.println(error.getMessage()); }
        }

        try (Scanner input_scanner = new Scanner(System.in)) {

            System.out.print("Введите значение W: "); var w_val = input_scanner.nextInt();
            System.out.print("Введите значение H: "); var h_val = input_scanner.nextInt();

            System.out.print("Введите значение S: "); var s_val = input_scanner.nextInt();
            System.out.print("Введите тип фигуры [ромб = 0; шахматы = 1]: ");

            var type_val = TaskType.values()[input_scanner.nextInt()];
            System.out.println(new LabTask5(w_val, h_val, s_val).generateVisual(type_val));
        }
        catch (Exception error) { System.out.println(error.getMessage()); }
    }
}
