package exercise2;
import java.io.*;
import java.util.Scanner;
/**
 * Клас для тестування коректності результатів обчислень та серіалізації/десеріалізації.
 * Виконується обчислення функції та збереження результатів у файл з можливістю їх відновлення.
 * @version 1.0
 */
public class mainEx4 {
    // Ім'я файлу для серіалізації.
    private static final String FILE_NAME = "calculation_result.ser";
    // Об'єкт для збереження результату обчислень.
    private CalculationResult result;
    // Клас для зберігання результатів обчислень. Містить два поля: аргумент та результат функції.
    public static class CalculationResult implements Serializable {
        private double argument;
        private double result;

        // Конструктор ініціалізує значення аргументу і результату.
        public CalculationResult(double argument, double result) {
            this.argument = argument;
            this.result = result;
        }
        // Отримує значення аргументу.
        public double getArgument() {
            return argument;
        }
        // Отримує результат обчислення функції.
        public double getResult() {
            return result;
        }
        // Повертає строкове подання результату обчислення.
        @Override
        public String toString() {
            return "Argument: " + argument + ", Result: " + result;
        }
    }
    // Конструктор класу CalcTest ініціалізує об'єкт для зберігання результату.
    public mainEx4 () {
        result = new CalculationResult(0.0, 0.0);
    }
    /**
     * Виконує обчислення значення функції для заданого аргументу.
     * У нашому випадку функція - синус від значення аргументу.
     * @param argument значення аргументу функції.
     */
    public void calculate(double argument) {
        double resultValue = Math.sin(Math.toRadians(argument));  // обчислення синуса
        result = new CalculationResult(argument, resultValue);
    }
    /**
     * Зберігання результату обчислення в файл.
     * @throws IOException Якщо виникла помилка при збереженні.
     */
    public void saveResult() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(result);
        }
    }
    /**
     * Відновлення результатів з файлу.
     * @throws IOException Якщо виникла помилка при зчитуванні.
     * @throws ClassNotFoundException Якщо клас не знайдений.
     */
    public void restoreResult() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            result = (CalculationResult) ois.readObject();
        }
    }
    public void showResult() {
        System.out.println(result);
    }
     // Тестування коректності обчислень та серіалізації/десеріалізації.
    public static void main(String[] args) {
        try {
            mainEx4 test = new mainEx4();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть число (в градусах) для обчислення синуса: ");
            double userInput = scanner.nextDouble();
            test.calculate(userInput);
            test.showResult();
            test.saveResult();
            test.restoreResult();
            test.showResult();
            // Перевірка коректності серіалізації/десеріалізації
            assert test.result.getArgument() == userInput : "Аргумент не збігається";
            assert Math.abs(test.result.getResult() - Math.sin(Math.toRadians(userInput))) < 1e-10 : "Результат не збігається";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
