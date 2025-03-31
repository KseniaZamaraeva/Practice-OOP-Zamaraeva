package exercise6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.*;

class ApplicationTest {
    private List<ComputationResult> results;
    private BlockingQueue<Task> taskQueue;

    @BeforeEach
    void setUp() {
        results = new ArrayList<>();
        results.add(new IsoscelesTriangle(6, 5)); // Периметр = 16
        results.add(new Rectangle(3, 4)); // Периметр = 14
        taskQueue = new LinkedBlockingQueue<>();
    }

    @Test
    void testResizeTask() throws InterruptedException {
        ResizeTask resizeTask = new ResizeTask(new ArrayList<>(results), 2);
        taskQueue.add(resizeTask);
        resizeTask.execute();

        assertEquals(32, results.get(0).value, 0.01);
        assertEquals(28, results.get(1).value, 0.01);
    }

    @Test
    void testParallelProcessing() {
        ParallelProcessor.process(results);

        double min = results.stream().mapToDouble(r -> r.value).min().orElse(Double.NaN);
        double max = results.stream().mapToDouble(r -> r.value).max().orElse(Double.NaN);
        double avg = results.stream().mapToDouble(r -> r.value).average().orElse(Double.NaN);

        assertEquals(14, min, 0.01);
        assertEquals(16, max, 0.01);
        assertEquals(15, avg, 0.01);
    }
}
