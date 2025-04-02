package exercise7;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

interface ExecutableTask {
    void execute();
}
class ComputationData {
    protected double value;
    public ComputationData(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }
}
class RectangleShape extends ComputationData {
    public RectangleShape(double width, double height) {
        super(2 * (width + height));
    }
}
class TriangleShape extends ComputationData {
    public TriangleShape(double base, double side) {
        super(2 * side + base);
    }
}
class ScalingTask implements ExecutableTask {
    private List<ComputationData> results;
    private double factor;
    public ScalingTask(List<ComputationData> results, double factor) {
        this.results = results;
        this.factor = factor;
    }
    @Override
    public void execute() {
        for (ComputationData result : results) {
            result.value *= factor;
        }
    }
}
class DataProcessor {
    public static void process(List<ComputationData> results) {
        OptionalDouble min = results.stream().mapToDouble(ComputationData::getValue).min();
        OptionalDouble max = results.stream().mapToDouble(ComputationData::getValue).max();
        OptionalDouble avg = results.stream().mapToDouble(ComputationData::getValue).average();

        JOptionPane.showMessageDialog(null, "Мінімальне: " + min.orElse(Double.NaN) + "\n" +
                "Максимальне: " + max.orElse(Double.NaN) + "\n" + "Середнє: " + avg.orElse(Double.NaN));
    }
}
class TaskWorker implements Runnable {
    private BlockingQueue<ExecutableTask> taskQueue;
    public TaskWorker(BlockingQueue<ExecutableTask> taskQueue) {
        this.taskQueue = taskQueue;
    }
    @Override
    public void run() {
        try {
            while (true) {
                ExecutableTask task = taskQueue.take();
                task.execute();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Головний клас програми для обчислення параметрів геометричних фігур.
 * Доступні операції: додавання прямокутників та трикутників, масштабування, обчислення статистичних параметрів.
 */
public class main7 {
    private JFrame frame;
    private JTextArea outputArea;
    private List<ComputationData> results = new ArrayList<>();
    private BlockingQueue<ExecutableTask> taskQueue = new LinkedBlockingQueue<>();
    private Thread worker;
    public main7() {
        frame = new JFrame("Індивідуальне завдання 'Обчислення периметрів фігур'");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("E:\\клонGitHub\\Practice-OOP-Zamaraeva\\OOP-Practice\\src\\image\\icon.png");  // Вкажіть шлях до вашої іконки
        frame.setIconImage(icon.getImage());  // Задаємо іконку

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        // Фонова панель з градієнтом і текстовий заголовок
        JPanel visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Плавний градієнт для фону
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, 600, 500, Color.ORANGE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("Практика з об'єктно-орієнтованого програмування", 30, 50);
                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                g2d.drawString("Java", 30, 80);
            }
        };
        visualPanel.setPreferredSize(new Dimension(600, 150));
        frame.add(visualPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addRectangleBtn = new JButton("Додати прямокутник");
        JButton addTriangleBtn = new JButton("Додати трикутник");
        JButton scaleBtn = new JButton("Масштабувати");
        JButton processBtn = new JButton("Обробити");
        JButton displayBtn = new JButton("Відобразити результати");
        JButton instructionsBtn = new JButton("Інструкція");
        JButton exitBtn = new JButton("Вийти");

        buttonPanel.add(addRectangleBtn);
        buttonPanel.add(addTriangleBtn);
        buttonPanel.add(scaleBtn);
        buttonPanel.add(processBtn);
        buttonPanel.add(displayBtn);
        buttonPanel.add(instructionsBtn);
        buttonPanel.add(exitBtn);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        addRectangleBtn.addActionListener(this::addRectangle);
        addTriangleBtn.addActionListener(this::addTriangle);
        scaleBtn.addActionListener(this::scaleShapes);
        processBtn.addActionListener(this::processResults);
        displayBtn.addActionListener(this::displayResults);
        instructionsBtn.addActionListener(this::showInstructions);
        exitBtn.addActionListener(e -> System.exit(0));

        worker = new Thread(new TaskWorker(taskQueue));
        worker.start();

        frame.setVisible(true);
    }
    private void addRectangle(ActionEvent e) {
        double width = Double.parseDouble(JOptionPane.showInputDialog("Введіть ширину:"));
        double height = Double.parseDouble(JOptionPane.showInputDialog("Введіть висоту:"));
        results.add(new RectangleShape(width, height));
        outputArea.append("Додано прямокутник (" + width + " x " + height + ")\n");
    }
    private void addTriangle(ActionEvent e) {
        double base = Double.parseDouble(JOptionPane.showInputDialog("Введіть основу:"));
        double side = Double.parseDouble(JOptionPane.showInputDialog("Введіть сторону:"));
        results.add(new TriangleShape(base, side));
        outputArea.append("Додано трикутник (" + base + " x " + side + ")\n");
    }
    private void scaleShapes(ActionEvent e) {
        double factor = Double.parseDouble(JOptionPane.showInputDialog("Введіть коефіцієнт масштабування:"));
        taskQueue.add(new ScalingTask(new ArrayList<>(results), factor));
        outputArea.append("Здійснено масштабування з коефіцієнтом " + factor + "\n");
    }
    private void processResults(ActionEvent e) {
        DataProcessor.process(results);
    }
    private void displayResults(ActionEvent e) {
        outputArea.append("\nРезультати:\n");
        for (ComputationData result : results) {
            outputArea.append("- " + result.getValue() + "\n");
        }
    }
    // Метод для відображення інструкцій
    private void showInstructions(ActionEvent e) {
        String instructions = "Інструкція користувача:\n\n" +
                "1. Додайте геометричні фігури (прямокутники та трикутники) за допомогою кнопок 'Додати прямокутник' та 'Додати трикутник'.\n" +
                "2. Введіть параметри фігур: для прямокутника - ширину та висоту, для трикутника - основу та сторону.\n" +
                "3. Масштабуйте фігури, використовуючи кнопку 'Масштабувати', та введіть коефіцієнт масштабування.\n" +
                "4. Натисніть 'Обробити', щоб отримати мінімальне, максимальне та середнє значення параметрів.\n" +
                "5. Використовуйте кнопку 'Відобразити результати' для перегляду значень.\n" +
                "6. Натискайте 'Вийти', щоб закрити програму.";

        JOptionPane.showMessageDialog(frame, instructions, "Інструкція", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(main7::new);
    }
}
