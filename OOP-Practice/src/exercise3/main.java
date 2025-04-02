package exercise3;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// Інтерфейс для фабрикованих об'єктів
interface DisplayableResult {
    void display(); // Метод для відображення результатів
}
// Клас для результату обчислення периметра рівнобедреного трикутника
class TrianglePerimeterResult implements DisplayableResult {
    private double perimeter;

    public TrianglePerimeterResult(double perimeter) {
        this.perimeter = perimeter;
    }
    @Override
    public void display() {
        System.out.println("Периметр рівнобедреного трикутника: " + perimeter);
    }
}
// Клас для результату обчислення периметра прямокутника
class RectanglePerimeterResult implements DisplayableResult {
    private double perimeter;
    public RectanglePerimeterResult(double perimeter) {
        this.perimeter = perimeter;
    }
    @Override
    public void display() {
        System.out.println("Периметр прямокутника: " + perimeter);
    }
}
// Клас для підрахунку кількості одиничних бітів у двійковому представленні
class BinaryOnesResult implements DisplayableResult {
    private int onesCount;
    public BinaryOnesResult(int onesCount) {
        this.onesCount = onesCount;
    }
    @Override
    public void display() {
        System.out.println("Кількість одиничних бітів у двійковому представленні: " + onesCount);
    }
}
// Абстрактний клас для фабрики
abstract class ResultFactory {
    public abstract DisplayableResult createResult(double value);
}
// Фабрика для створення результату периметра рівнобедреного трикутника
class TrianglePerimeterFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(double value) {
        return new TrianglePerimeterResult(value);
    }
}
// Фабрика для створення результату периметра прямокутника
class RectanglePerimeterFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(double value) {
        return new RectanglePerimeterResult(value);
    }
}
// Фабрика для підрахунку одиничних бітів
class BinaryOnesFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(double value) {
        int intValue = (int) value;
        int onesCount = Integer.bitCount(intValue);
        return new BinaryOnesResult(onesCount);
    }
}
// Головний клас програми
public class main {
    // Метод для обчислення периметра рівнобедреного трикутника
    public static double calculateIsoscelesTrianglePerimeter(double a, double h) {
        double b = Math.sqrt((a / 2) * (a / 2) + h * h);
        return 2 * b + a;
    }
    // Метод для обчислення периметра прямокутника
    public static double calculateRectanglePerimeter(double a, double b) {
        return 2 * (a + b);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<DisplayableResult> results = new ArrayList<>();
        // Фабрики для створення результатів
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

        // Відображення результатів
        System.out.println("\nРезультати обчислень:");
        for (DisplayableResult result : results) {
            result.display();
        }
        scanner.close();
    }
}
