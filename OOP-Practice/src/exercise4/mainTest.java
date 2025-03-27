package exercise4;

import java.util.List;

/**
 * Клас для тестування основної функціональності
 */
public class mainTest {
    public static void main(String[] args) {
        // Запускаємо основний клас main4 для отримання результатів
        main4.main(new String[]{});

        // Отримуємо результати, введені користувачем
        List<DisplayableResult> results = main4.getResults();

        // Виведення результатів тесту
        System.out.println("\nРезультати тестування:");
        for (DisplayableResult result : results) {
            result.display();
        }
    }
}
