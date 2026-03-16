
/**
 * Write a description of class Cup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
public class Cup extends Item
{
    private Rectangle leftSide;
    private Rectangle base;
    private Rectangle rightSide; 
    
    /**
     * Constructor for objects of class Cup
     * @param number: Cup number
     * @param color: Cup color
     */
    public Cup(int number){
        super(number);
        String color = getColorForNumber(number);
        // Create the three rectangles that make up the cup
        leftSide = new Rectangle();
        base = new Rectangle();
        rightSide = new Rectangle();
        
        //Sets color
        leftSide.changeColor(color);
        base.changeColor(color);
        rightSide.changeColor(color);
    }
    
    /**
     * Draw the cup on the canvas - lado izquierdo, lado derecho y base
     */
    public void draw(){
        if (getIsVisible()) {
            int pixelSize = getSize() * 30; // Alto total en píxeles
            int halfWidth = pixelSize / 2;
            int sideWidth = 15; // Ancho de los lados (un poco más gruesos para que se vean)
            int baseHeight = 30; // Altura de la base (1cm)
            
            // LADO IZQUIERDO - rectángulo vertical del lado izquierdo
            leftSide.changeSize(pixelSize, sideWidth);
            leftSide.setPosition(getXPosition() - halfWidth, getYPosition());
            leftSide.makeVisible();
            
            // LADO DERECHO - rectángulo vertical del lado derecho
            rightSide.changeSize(pixelSize, sideWidth);
            rightSide.setPosition(getXPosition() + halfWidth - sideWidth, getYPosition());
            rightSide.makeVisible();
            
            // BASE - rectángulo horizontal en la parte inferior
            base.changeSize(baseHeight, pixelSize);
            base.setPosition(getXPosition() - halfWidth, getYPosition() + pixelSize - baseHeight);
            base.makeVisible();
            
            System.out.println("Cup #" + getNumber() + " dibujada en Y=" + getYPosition());
        }
    }
    
    /**
     * Erase the cup from the canvas
     */
    public void erase() {
        if (getIsVisible()) {
            leftSide.makeInvisible();
            base.makeInvisible();
            rightSide.makeInvisible();
        }
    }
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cup cup = (Cup) o;
        
        return getNumber() == this.getNumber() && getYPosition() == this.getYPosition();
    }
    
}