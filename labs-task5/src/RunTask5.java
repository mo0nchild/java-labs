import java.util.Scanner;

public class RunTask5 {

    public static void main(String[] args) {
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
