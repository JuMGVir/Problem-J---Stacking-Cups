
/**
 * Write a description of class Cup here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.*;


public class Cup{
    private int size; //Use cm units, not pixels
    private String color;
    private boolean isVisible;
    private ArrayList<ArrayList<Rectangle>> contour = new ArrayList<>();
    
    public Cup(int size, String color){
        ArrayList pieces = new ArrayList<>();
        pieces.add(new Rectangle());
        pieces.add(new Rectangle());
        pieces.add(new Rectangle());
        this.contour.add(pieces);
    }
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, this.contour.get(0).get(0));
            
        }
    }
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
