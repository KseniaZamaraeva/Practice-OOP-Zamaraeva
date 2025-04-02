package exercise2;
import java.util.*;
public class mainEx2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> arguments = new ArrayList<>();
        System.out.println("Введіть аргументи (для завершення введення залиште порожній рядок):");
        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            arguments.add(input);
        }
        // Виведення введених даних
        System.out.println("Аргументи:");
        if (arguments.isEmpty()) {
            System.out.println("Немає аргументів.");
        } else {
            for (int i = 0; i < arguments.size(); i++) {
                System.out.println("Аргумент " + (i + 1) + ": " + arguments.get(i));
            }
        }
        // Обчислення суми числових аргументів
        int sum = 0;
        for (String arg : arguments) {
            try {
                sum += Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                System.out.println("Не вдалось перетворити аргумент " + arg + " на число.");
            }
        }
        System.out.println("Сума числових аргументів: " + sum);
    }
}
