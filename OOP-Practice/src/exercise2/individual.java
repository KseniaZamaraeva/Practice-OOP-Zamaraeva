package exercise2;

import java.util.Scanner;

public class individual {

    /**
     * Метод для обчислення периметра рівнобедреного трикутника.
     * Периметр рівнобедреного трикутника обчислюється як 2 * b + a,
     * де b — одна з рівних сторін, а a — основа.
     * @param a довжина основи рівнобедреного трикутника
     * @param h висота рівнобедреного трикутника
     * @return периметр рівнобедреного трикутника
     */
    public static double calculateIsoscelesTrianglePerimeter(double a, double h) {
        // Обчислення рівних сторін трикутника за теоремою Піфагора
        double b = Math.sqrt((a / 2) * (a / 2) + h * h); // довжина рівних сторін
        return 2 * b + a;
    }

    /**
     * Метод для обчислення периметра прямокутника.
     * Периметр прямокутника обчислюється як 2 * (a + b),
     * де a та b — довжини сторін прямокутника.
     * @param a довжина сторони прямокутника
     * @param b ширина сторони прямокутника
     * @return периметр прямокутника
     */
    public static double calculateRectanglePerimeter(double a, double b) {
        return 2 * (a + b);
    }

    /**
     * Метод для підрахунку кількості одиничних бітів у двійковому представленні числа.
     * @param number число для підрахунку одиничних бітів
     * @return кількість одиничних бітів у двійковому представленні числа
     */
    public static int countOnesInBinary(int number) {
        int count = 0;
        while (number != 0) {
            count += (number & 1);
            number >>= 1;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Введення параметрів для рівнобедреного трикутника
        System.out.print("Введіть довжину основи рівнобедреного трикутника: ");
        double triangleBase = scanner.nextDouble();
        System.out.print("Введіть висоту рівнобедреного трикутника: ");
        double triangleHeight = scanner.nextDouble();

        // Введення параметрів для прямокутника
        System.out.print("Введіть довжину сторони прямокутника: ");
        double rectangleLength = scanner.nextDouble();
        System.out.print("Введіть ширину сторони прямокутника: ");
        double rectangleWidth = scanner.nextDouble();

        // Обчислення периметрів
        double trianglePerimeter = calculateIsoscelesTrianglePerimeter(triangleBase, triangleHeight);
        double rectanglePerimeter = calculateRectanglePerimeter(rectangleLength, rectangleWidth);

        // Обчислення суми периметрів
        double totalPerimeter = trianglePerimeter + rectanglePerimeter;

        // Приведення суми периметрів до цілого числа
        int totalPerimeterInt = (int) totalPerimeter;

        // Підрахунок кількості одиничних бітів у двійковому представленні числа
        int onesCount = countOnesInBinary(totalPerimeterInt);

        // Виведення результату
        System.out.println("Сума периметрів: " + totalPerimeter);
        System.out.println("Ціле число суми периметрів: " + totalPerimeterInt);
        System.out.println("Кількість одиничних бітів у двійковому представленні суми периметрів: " + onesCount);
    }
}
