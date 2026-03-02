

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
        String[] expectedItems = {"cup", "2"};
        Tower tower = new Tower(500,1000);
        tower.pushCup(2);
        assertEquals("Se obtuvo el valor esperado en la lista items",expectedItems,tower.items.get(0));
    }
    
    @Test
    public void accordingGRShouldCreateALid(){
        String[] expectedItems = {"lid", "3"};
        Tower tower = new Tower(500, 1000);
        tower.pushLid(3);
        assertEquals("Se obtuvo el valor esperado en la lista items",expectedItems,tower.items.get(0));
    }
    
    @Test
    public void accordingGRShouldCreateICups(){
        String[][] expectedItems = {{"cup", "1"},{"cup", "2"},{"cup", "3"}};
        Tower tower = new Tower(3);
        for(int i = 0; i<3; i++){
            assertArrayEquals("Se obtuvo el valor esperado en la lista items", expectedItems[i], tower.items.get(i));
        }
        
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