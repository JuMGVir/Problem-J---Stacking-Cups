
/**
 * Write a description of class Tower here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.*;

//Conversion 1cm = 10px
public class Tower{
    private int width;
    private int maxHeight;
    private boolean isVisible;
    private List<Cup> cups;
    /**
     * Create Canvas with set width and height
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        this.makeVisible();
    }
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    public void draw(){
        if(isVisible){
            Canvas canvas = Canvas.getCanvas();
            canvas.draw("Tower", "white",
                new java.awt.Rectangle(0,0,this.width, this.maxHeight));
        }
    }
    public void erase(){
        if(isVisible){
            Canvas canvas = Canvas.getCanvas();
            canvas.erase("Tower"); 
            
        } 
    }
}