import java.util.Scanner;

public class RunTask6 {

    public static void main(String[] args) {
        var scanner_instance = new Scanner(System.in);

        System.out.print("Введите значение X: ");
        var x_val = scanner_instance.nextDouble();

        System.out.print("Введите значение N: ");
        var n_val = scanner_instance.nextInt();

        System.out.print("Введите значение E: ");
        var e_val = scanner_instance.nextDouble();
        try {
            System.out.print("Выберите функцию [1 = cos(x), 2 = e^x]: ");

            LabTask6 task = switch (scanner_instance.nextInt()) {
                case 1 -> new CosCalculator(n_val, e_val);
                case 2 -> new EXCalculator(n_val, e_val);
                default -> throw new Exception("Неверное задание");
            };

            System.out.printf("Вычисление с N: [%.4f]\n", task.calculateByN(x_val));
            System.out.printf("Вычисление с E: [%.4f]\n", task.calculateByE(x_val));

            System.out.printf("Вычисление с E/10: [%.4f]\n",task.calculateByEDiv10(x_val));
            System.out.printf("Вычисление с MATH: [%.4f]\n", task.calculateByMath(x_val));
        }
        catch (Exception error) { System.out.println(error.getMessage()); }
    }
}
