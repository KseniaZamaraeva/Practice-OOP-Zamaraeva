package exercise4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Інтерфейс для фабрикованих об'єктів
 */
interface DisplayableResult {
    void display();
    void displayAsTable();
}

/**
 * Базовий клас для результатів, що включає загальні методи форматування
 */
abstract class BaseResult implements DisplayableResult {
    protected double value;
    protected String description;

    public BaseResult(double value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public void displayAsTable() {
        System.out.printf("| %-40s | %10.2f |\n|", description, value);
    }
}

/**
 * Клас для результату обчислення периметра рівнобедреного трикутника
 */
class TrianglePerimeterResult extends BaseResult {
    public TrianglePerimeterResult(double perimeter) {
        super(perimeter, "Периметр рівнобедреного трикутника");
    }

    @Override
    public void display() {
        System.out.println(description + ": " + value);
    }
}

/**
 * Клас для результату обчислення периметра прямокутника
 */
class RectanglePerimeterResult extends BaseResult {
    public RectanglePerimeterResult(double perimeter) {
        super(perimeter, "Периметр прямокутника");
    }

    @Override
    public void display() {
        System.out.println(description + ": " + value);
    }
}

/**
 * Клас для підрахунку кількості одиничних бітів у двійковому представленні
 */
class BinaryOnesResult extends BaseResult {
    private int onesCount;

    public BinaryOnesResult(int onesCount) {
        super(onesCount, "К-ть одиничних бітів у двійковому представленні");
        this.onesCount = onesCount;
    }

    @Override
    public void display() {
        System.out.println(description + ": " + onesCount);
    }
}

/**
 * Абстрактний клас для фабрики
 */
abstract class ResultFactory {
    public abstract DisplayableResult createResult(double value);
}

/**
 * Фабрика для створення результату периметра рівнобедреного трикутника
 */
class TrianglePerimeterFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(double value) {
        return new TrianglePerimeterResult(value);
    }
}

/**
 * Фабрика для створення результату периметра прямокутника
 */
class RectanglePerimeterFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(double value) {
        return new RectanglePerimeterResult(value);
    }
}

/**
 * Фабрика для підрахунку одиничних бітів
 */
class BinaryOnesFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(double value) {
        int intValue = (int) value;
        int onesCount = Integer.bitCount(intValue);
        return new BinaryOnesResult(onesCount);
    }
}

/**
 * Головний клас програми
 */
public class main4 {
    // Метод для обчислення периметра рівнобедреного трикутника
    public static double calculateIsoscelesTrianglePerimeter(double a, double h) {
        double b = Math.sqrt((a / 2) * (a / 2) + h * h);
        return 2 * b + a;
    }

    // Метод для обчислення периметра прямокутника
    public static double calculateRectanglePerimeter(double a, double b) {
        return 2 * (a + b);
    }

    private static final List<DisplayableResult> results = new ArrayList<>();

    public static List<DisplayableResult> getResults() {
        return results;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TrianglePerimeterFactory triangleFactory = new TrianglePerimeterFactory();
        RectanglePerimeterFactory rectangleFactory = new RectanglePerimeterFactory();
        BinaryOnesFactory binaryOnesFactory = new BinaryOnesFactory();

        System.out.print("Введіть довжину основи рівнобедреного трикутника: ");
        double triangleBase = scanner.nextDouble();
        System.out.print("Введіть висоту рівнобедреного трикутника: ");
        double triangleHeight = scanner.nextDouble();

        System.out.print("Введіть довжину сторони прямокутника: ");
        double rectangleLength = scanner.nextDouble();
        System.out.print("Введіть ширину сторони прямокутника: ");
        double rectangleWidth = scanner.nextDouble();

        double trianglePerimeter = calculateIsoscelesTrianglePerimeter(triangleBase, triangleHeight);
        double rectanglePerimeter = calculateRectanglePerimeter(rectangleLength, rectangleWidth);
        double totalPerimeter = trianglePerimeter + rectanglePerimeter;

        results.add(triangleFactory.createResult(trianglePerimeter));
        results.add(rectangleFactory.createResult(rectanglePerimeter));
        results.add(binaryOnesFactory.createResult(totalPerimeter));

        System.out.print("Оберіть формат виведення результатів (1 - звичайний текстовий, 2 - табличний): ");
        int choice = scanner.nextInt();

        if (choice == 2) {
            System.out.println("\nРезультати у табличному форматі:");
            System.out.println("---------------------------------------------------------");
            System.out.println("| Опис результату:                         | Значення:  |");
            System.out.println("|------------------------------------------|------------|");
            for (DisplayableResult result : results) {

                result.displayAsTable();
                System.out.println("------------------------------------------|------------|");
            }

    } else {
            System.out.println("\nРезультати у звичайному форматі:");
            for (DisplayableResult result : results) {
                result.display();
            }
        }

        scanner.close();
    }
}
