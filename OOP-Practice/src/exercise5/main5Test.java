package exercise5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class main5Test {
    private List<DisplayableResult> results;
    private Menu menu;

    @BeforeEach
    void setUp() {
        results = new ArrayList<>();
        results.add(new Triangle(3, 4, 5)); // Периметр = 12
        results.add(new Rectangle(2, 3)); // Периметр = 10
        menu = Menu.getInstance();
    }

    @Test
    void testScaleCommand() {
        ScaleCommand scaleCommand = new ScaleCommand(results, 2);
        menu.executeCommand(scaleCommand);

        assertEquals(24, ((BaseResult) results.get(0)).value, 0.01);
        assertEquals(20, ((BaseResult) results.get(1)).value, 0.01);
    }

    @Test
    void testNormalizeCommand() {
        NormalizeCommand normalizeCommand = new NormalizeCommand(results);
        menu.executeCommand(normalizeCommand);

        double max = Math.max(((BaseResult) results.get(0)).value, ((BaseResult) results.get(1)).value);
        assertEquals(1.0, ((BaseResult) results.get(0)).value / max, 0.01);
        assertEquals(1.0, ((BaseResult) results.get(1)).value / max, 0.01);
    }

    @Test
    void testSortCommand() {
        SortCommand sortCommand = new SortCommand(results);
        menu.executeCommand(sortCommand);

        assertTrue(((BaseResult) results.get(0)).value <= ((BaseResult) results.get(1)).value);
    }
}
