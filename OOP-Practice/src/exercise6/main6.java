package exercise6;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

interface Task {
    void execute();
    void undo();
}

interface Displayable {
    void display();
}

class ComputationResult implements Displayable {
    double value;

    public ComputationResult(double value) {
        this.value = value;
    }

    @Override
    public void display() {
        System.out.println("Результат: " + value);
    }
}

class Rectangle extends ComputationResult {
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

class IsoscelesTriangle extends ComputationResult {
    double base, side;

    public IsoscelesTriangle(double base, double side) {
        super(2 * side + base);
        this.base = base;
        this.side = side;
    }

    @Override
    public void display() {
        System.out.printf("Рівнобедрений трикутник -> Периметр: %.2f\n", value);
    }
}

class ResizeTask implements Task {
    private List<Displayable> results;
    private double factor;
    private List<Double> previousValues = new ArrayList<>();

    public ResizeTask(List<Displayable> results, double factor) {
        this.results = results;
        this.factor = factor;
    }

    @Override
    public void execute() {
        previousValues.clear();
        for (Displayable result : results) {
            if (result instanceof ComputationResult) {
                previousValues.add(((ComputationResult) result).value);
                ((ComputationResult) result).value *= factor;
            }
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) instanceof ComputationResult) {
                ((ComputationResult) results.get(i)).value = previousValues.get(i);
            }
        }
    }
}

class ParallelProcessor {
    public static void process(List<ComputationResult> results) {
        OptionalDouble min = results.parallelStream().mapToDouble(r -> r.value).min();
        OptionalDouble max = results.parallelStream().mapToDouble(r -> r.value).max();
        OptionalDouble avg = results.parallelStream().mapToDouble(r -> r.value).average();
        List<ComputationResult> filtered = results.parallelStream()
                .filter(r -> r.value > 10)
                .collect(Collectors.toList());

        System.out.println("Мінімальне значення: " + min.orElse(Double.NaN));
        System.out.println("Максимальне значення: " + max.orElse(Double.NaN));
        System.out.println("Середнє значення: " + avg.orElse(Double.NaN));
        System.out.println("Фільтровані значення (більше 10): " + filtered.size());
    }
}

class WorkerThread implements Runnable {
    private BlockingQueue<Task> taskQueue;

    public WorkerThread(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = taskQueue.take();
                task.execute();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class main6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<ComputationResult> results = new ArrayList<>();
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
        Thread worker = new Thread(new WorkerThread(taskQueue));
        worker.start();

        System.out.println("Введіть кількість фігур:");
        int numShapes = scanner.nextInt();

        for (int i = 0; i < numShapes; i++) {
            System.out.println("Оберіть тип фігури (1 - Прямокутник, 2 - Рівнобедрений трикутник):");
            int type = scanner.nextInt();

            if (type == 1) {
                System.out.println("Введіть ширину та висоту прямокутника:");
                double width = scanner.nextDouble();
                double height = scanner.nextDouble();
                results.add(new Rectangle(width, height));
            } else if (type == 2) {
                System.out.println("Введіть основу та сторону рівнобедренного трикутника:");
                double base = scanner.nextDouble();
                double side = scanner.nextDouble();
                results.add(new IsoscelesTriangle(base, side));
            } else {
                System.out.println("Невірний вибір!");
            }
        }

        while (true) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Масштабування");
            System.out.println("2 - Паралельна обробка");
            System.out.println("3 - Вивести результати");
            System.out.println("4 - Вийти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Введіть коефіцієнт масштабування: ");
                    double factor = scanner.nextDouble();
                    taskQueue.add(new ResizeTask(new ArrayList<>(results), factor));
                    break;
                case 2:
                    ParallelProcessor.process(results);
                    break;
                case 3:
                    for (ComputationResult result : results) {
                        result.display();
                    }
                    break;
                case 4:
                    System.out.println("Завершення програми.");
                    worker.interrupt();
                    return;
                default:
                    System.out.println("Невірний вибір. Введіть число від 1 до 4.");
            }
        }
    }
}