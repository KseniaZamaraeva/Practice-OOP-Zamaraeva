package exercise2;

import java.io.*;
import java.util.*;

public class mainEx1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> argumentsList = new ArrayList<>();

        // Введення даних з клавіатури
        System.out.println("Введіть аргументи (для завершення введення залиште порожній рядок):");

        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            argumentsList.add(input);
        }

        // Виведення введених даних
        System.out.println("Аргументи, введені з клавіатури:");
        if (argumentsList.isEmpty()) {
            System.out.println("Немає аргументів.");
        } else {
            for (int i = 0; i < argumentsList.size(); i++) {
                System.out.println("Аргумент " + (i + 1) + ": " + argumentsList.get(i));
            }
        }

        // Збереження результатів у файл
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("arguments.ser"))) {
            oos.writeObject(argumentsList);
            System.out.println("Результати збережено у файл arguments.ser.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Відновлення результатів із файлу
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("arguments.ser"))) {
            List<String> restoredList = (List<String>) ois.readObject();
            System.out.println("\nВідновлені аргументи з файлу:");
            for (int i = 0; i < restoredList.size(); i++) {
                System.out.println("Аргумент " + (i + 1) + ": " + restoredList.get(i));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

