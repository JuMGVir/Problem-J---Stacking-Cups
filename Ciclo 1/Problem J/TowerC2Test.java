import java.util.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TowerC2Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TowerC2Test
{
    /**
     * Default constructor for test class TowerC2Test
     */
    public TowerC2Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    
    @Test
    public void accordingGRShouldCreateACup(){
        List<Item> expectedItems = new ArrayList<>();
        Cup newCup = new Cup(2);
        expectedItems.add(newCup);
        Tower tower = new Tower(500,1000);
        tower.pushCup(2);
        assertTrue("Se obtuvo el valor esperado en la lista items",expectedItems.equals(tower.getItems()));
        
        
    }
    
    @Test
    public void accordingGRShouldCreateALid(){
        List<Item> expectedItems = new ArrayList<>();
        Lid newLid = new Lid(2);
        expectedItems.add(newLid);
        Tower tower = new Tower(500,1000);
        tower.pushLid(2);
        assertTrue("Se obtuvo el valor esperado en la lista items", expectedItems.equals(tower.getItems()));
    }
    
    @Test
    public void accordingGRShouldCreateICups(){
        List<Item> expectedItems = new ArrayList<>();
        Tower tower = new Tower(5);
        for(int i = 1 ; i<=5 ; i++){
            Cup newCup = new Cup(i);
            expectedItems.add(newCup);
        }
        assertTrue("Se obtuvo el valor esperado en la lista items", expectedItems.equals(tower.getItems()));
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}