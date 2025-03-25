package exercise2;

import java.io.*;
import java.util.Scanner;

class Car implements Serializable {
    private String model;
    private int year;
    private transient String engineNumber; // Поле, яке не буде серіалізовано

    public Car(String model, int year, String engineNumber) {
        this.model = model;
        this.year = year;
        this.engineNumber = engineNumber;
    }

    @Override
    public String toString() {
        return "Car{model='" + model + "', year=" + year + ", engineNumber='" + engineNumber + "'}";
    }
}

public class mainEx3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Введення даних автомобіля
        System.out.print("Введіть модель автомобіля: ");
        String model = scanner.nextLine();

        System.out.print("Введіть рік випуску: ");
        int year = scanner.nextInt();
        scanner.nextLine();  // Очищаємо буфер після вводу числа

        System.out.print("Введіть номер двигуна: ");
        String engineNumber = scanner.nextLine();

        // Створення об'єкта Car з введеними даними
        Car car = new Car(model, year, engineNumber);

        // Серіалізація
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("car.ser"))) {
            out.writeObject(car);
            System.out.println("Автомобіль серіалізовано.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Виведення об'єкта перед відновленням
        System.out.println("Автомобіль перед відновленням: " + car);

        // Відновлення
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("car.ser"))) {
            Car restoredCar = (Car) in.readObject();
            System.out.println("Автомобіль відновлено: " + restoredCar);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
