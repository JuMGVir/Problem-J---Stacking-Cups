import java.util.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TowerContest_test {
    private TowerContest sol;

    @Before
    public void setUp() {
        sol = new TowerContest();
    }

    @Test
    public void shouldSolveIntermediateCase() {
        String result = sol.solve(4, 10);
        assertNotNull(result);
        assertTrue(result.contains("7"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("1"));
    }

    @Test
    public void shouldSolveMinimumHeight() {
        String result = sol.solve(3, 5);
        assertNotNull(result);
        assertEquals("5 3 1", result);
    }

    @Test
    public void shouldReturnNullForTooLowHeight() {
        String result = sol.solve(3, 4);
        assertNull(result);
    }

    @Test
    public void shouldSolveMaximumHeight() {
        String result = sol.solve(4, 16);
        assertNotNull(result);
        assertEquals("1 3 5 7", result);
    }

    @Test
    public void shouldReturnNullForTooHighHeight() {
        String result = sol.solve(3, 10);
        assertNull(result);
    }

    @Test
    public void shouldSolve() {
        String result = sol.solve(4, 13);
        assertNotNull(result);
        assertEquals(4, result.split(" ").length);
    }

    @After
    public void tearDown() {
        sol = null;
    }
}