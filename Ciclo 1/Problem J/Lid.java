
/**
 * Write a description of class Lid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;

public class Lid extends Item
{
    private Rectangle lid;
    
    /**
     * Constructor for objects of class Lid
     * @param number: Lid number (i)
     * @param color: Lid color
     */
    public Lid(int number){
        super(number);
        String color = getColorForNumber(number);
        // Crear el rectangulo de la tapa
        lid = new Rectangle();
        lid.changeColor(color);
    }
    
    /**
     * Draw the lid on the canvas
     */
    public void draw() {
        if (getIsVisible()) {
            int pixelWidth = getSize() * 30; // Ancho en píxeles
            int halfWidth = pixelWidth / 2;
            
            // La tapa es un rectángulo de 30px de alto (1cm)
            lid.changeColor(getColor());
            lid.changeSize(30, pixelWidth);
            lid.setPosition(getXPosition() - halfWidth, getYPosition());
            lid.makeVisible();
            
            System.out.println("Lid #" + getNumber() + " dibujada en Y=" + getYPosition());
        }
    }
    
    /**
     * Erase the lid from the canvas
     */
    public void erase() {
        if (getIsVisible()) {
            lid.makeInvisible();
        }
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lid lid = (Lid) o;
        
        return getNumber() == this.getNumber() && getYPosition() == this.getYPosition();
    }
}