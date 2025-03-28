package exercise5;

import java.util.*;

/**
 * Інтерфейс для команд.
 */
interface Command {
    void execute();
    void undo();
}

/**
 * Інтерфейс для відображення результатів.
 */
interface DisplayableResult {
    void display();
}

/**
 * Базовий клас для результатів.
 */
class BaseResult implements DisplayableResult {
    double value;

    public BaseResult(double value) {
        this.value = value;
    }

    @Override
    public void display() {
        System.out.println("Результат: " + value);
    }
}

/**
 * Клас для трикутника.
 */
class Triangle extends BaseResult {
    double a, b, c;

    public Triangle(double a, double b, double c) {
        super(a + b + c);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void display() {
        System.out.printf("Трикутник -> Периметр: %.2f\n", value);
    }
}

/**
 * Клас для прямокутника.
 */
class Rectangle extends BaseResult {
    double width, height;

    public Rectangle(double width, double height) {
        super(2 * (width + height));
        this.width = width;
        this.height = height;
    }

    @Override
    public void display() {
        System.out.printf("Прямокутник -> Периметр: %.2f\n", value);
    }
}


/**
 * Команда для масштабування.
 */
class ScaleCommand implements Command {
    private List<DisplayableResult> results;
    private double factor;
    private List<Double> previousValues = new ArrayList<>();

    public ScaleCommand(List<DisplayableResult> results, double factor) {
        this.results = results;
        this.factor = factor;
    }

    @Override
    public void execute() {
        previousValues.clear();
        for (DisplayableResult result : results) {
            if (result instanceof BaseResult) {
                previousValues.add(((BaseResult) result).value);
                ((BaseResult) result).value *= factor;
            }
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) instanceof BaseResult) {
                ((BaseResult) results.get(i)).value = previousValues.get(i);
            }
        }
    }
}

/**
 * Команда для нормалізації.
 */
class NormalizeCommand implements Command {
    private List<DisplayableResult> results;
    private List<Double> previousValues = new ArrayList<>();
    private double max;

    public NormalizeCommand(List<DisplayableResult> results) {
        this.results = results;
    }

    @Override
    public void execute() {
        max = results.stream()
                .filter(r -> r instanceof BaseResult)
                .mapToDouble(r -> ((BaseResult) r).value)
                .max().orElse(1);

        previousValues.clear();
        for (DisplayableResult result : results) {
            if (result instanceof BaseResult) {
                previousValues.add(((BaseResult) result).value);
                ((BaseResult) result).value /= max;
            }
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) instanceof BaseResult) {
                ((BaseResult) results.get(i)).value = previousValues.get(i);
            }
        }
    }
}

/**
 * Команда для сортування.
 */
class SortCommand implements Command {
    private List<DisplayableResult> results;
    private List<DisplayableResult> previousState;

    public SortCommand(List<DisplayableResult> results) {
        this.results = results;
    }

    @Override
    public void execute() {
        previousState = new ArrayList<>(results);
        results.sort(Comparator.comparingDouble(r -> ((BaseResult) r).value));
    }

    @Override
    public void undo() {
        results.clear();
        results.addAll(previousState);
    }
}

/**
 * Головний клас програми.
 */
public class main5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<DisplayableResult> results = new ArrayList<>();
        Menu menu = Menu.getInstance();

        // Введення фігур користувачем
        System.out.println("Введіть кількість фігур:");
        int numShapes = scanner.nextInt();

        for (int i = 0; i < numShapes; i++) {
            System.out.println("Оберіть тип фігури (1 - трикутник, 2 - прямокутник):");
            int type = scanner.nextInt();

            if (type == 1) {
                System.out.print("Введіть сторони трикутника (a, b, c): ");
                double a = scanner.nextDouble();
                double b = scanner.nextDouble();
                double c = scanner.nextDouble();
                results.add(new Triangle(a, b, c));
            } else if (type == 2) {
                System.out.print("Введіть ширину та висоту прямокутника: ");
                double width = scanner.nextDouble();
                double height = scanner.nextDouble();
                results.add(new Rectangle(width, height));
            } else {
                System.out.println("Невірний тип фігури!");
            }
        }

        while (true) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Масштабування");
            System.out.println("2 - Нормалізація");
            System.out.println("3 - Сортування");
            System.out.println("4 - Вивести результати");
            System.out.println("5 - Вийти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Введіть коефіцієнт масштабування: ");
                    double factor = scanner.nextDouble();
                    menu.executeCommand(new ScaleCommand(results, factor));
                    printResults(results); // Відображення після кожної команди
                    break;
                case 2:
                    menu.executeCommand(new NormalizeCommand(results));
                    printResults(results); // Відображення після кожної команди
                    break;
                case 3:
                    menu.executeCommand(new SortCommand(results));
                    printResults(results); // Відображення після кожної команди
                    break;
                case 4:
                    printResults(results);
                    break;
                case 5:
                    System.out.println("Завершення програми.");
                    return;
                default:
                    System.out.println("Невірний вибір. Введіть число від 1 до 5.");
            }
        }
    }

    public static void printResults(List<DisplayableResult> results) {
        System.out.println("\nПоточні результати:");
        for (DisplayableResult result : results) {
            result.display();
        }
    }
}

/**
 * Меню для управління командами.
 */
class Menu {
    private static Menu instance;
    private Stack<Command> history = new Stack<>();

    private Menu() {}

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }
}
